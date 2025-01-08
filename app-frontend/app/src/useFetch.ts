import {useEffect, useReducer, useRef} from "react";

/**
 * Represents the state of an HTTP request.
 * @template T - The type of data expected in the response.
 * @interface State
 * @property {T | undefined} data - The data received from the HTTP request.
 * @property {Error | undefined} error - An error object if the request encounters an error.
 */
interface State<T> {
  data?: T
  error?: Error
}

/**
 * Represents a cache of data for different URLs.
 * @template T - The type of data stored in the cache.
 * @type {object} Cache
 */
type Cache<T> = Record<string, T>

/**
 * Represents the possible actions that can be dispatched in the fetchReducer.
 * @template T - The type of data expected in the response.
 * @type {object} Action
 */
type Action<T> =
/** Indicates that the request is in progress. */
  | { type: 'loading' }
  /** Indicates that the request has been successfully fetched. */
  | { type: 'fetched'; payload: T }
  /** Indicates that an error occurred during the request. */
  | { type: 'error'; payload: Error }

const cache = {}

/**
 * Custom hook for making HTTP requests and managing the state of the request.
 * @template T - The type of data expected in the response.
 * @param {string | undefined} url - The URL to make the HTTP request to.
 * @param {T} defaultValue - The default value for the data (optional).
 * @param {RequestInit} options - The [options for the HTTP request]() (optional).
 * @returns {State<T>} The state object representing the result of the HTTP request.
 * @see [Documentation](https://usehooks-ts.com/react-hook/use-fetch)
 * @see [MDN Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API)
 * @example
 * const { data, error } = useFetch<User>('https://api.example.com/user');
 */
export function useFetch<T>(
  url?: string,
  defaultValue?: T,
  options?: RequestInit,
): State<T> {
  // Used to prevent state update if the component is unmounted
  const cancelRequest = useRef<boolean>(false)

  const initialState: State<T> = {
    error: undefined,
    data: defaultValue,
  }

  // Keep state logic separated
  const fetchReducer = (state: State<T>, action: Action<T>): State<T> => {
    switch (action.type) {
      case 'loading':
        return {...initialState}
      case 'fetched':
        return {...initialState, data: action.payload}
      case 'error':
        return {...initialState, error: action.payload}
      default:
        return state
    }
  }

  const [state, dispatch] = useReducer(fetchReducer, initialState)

  useEffect(() => {
    // Do nothing if the url is not given
    if (!url) return

    cancelRequest.current = false

    const fetchData = async () => {
      dispatch({type: 'loading'})

      // If a cache exists for this url, return it
      const currentCache = cache[url]
      if (currentCache) {
        dispatch({type: 'fetched', payload: currentCache})
        return
      } else {
        // Prevent race loading if the cache is empty
        cache[url] = defaultValue
      }

      try {
        const response = await fetch(url, options)
        if (!response.ok) {
          throw new Error(response.statusText)
        }

        const data = (await response.json()) as T
        cache[url] = data
        if (cancelRequest.current) return

        dispatch({type: 'fetched', payload: data})
      } catch (error) {
        if (cancelRequest.current) return

        dispatch({type: 'error', payload: error as Error})
      }
    }

    void fetchData()

    // Use the cleanup function for avoiding a possibly...
    // ...state update after the component was unmounted
    return () => {
      cancelRequest.current = true
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [url])

  return state
}
