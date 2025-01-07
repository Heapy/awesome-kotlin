import {create} from 'zustand';

interface ApiState<T> {
  data: T | null;
  loading: boolean;
  error: Error | null;
  fetch: (params?: Record<string, string>) => Promise<void>;
  cancel: () => void;
}

export function createApiStore<T>(
  {
    endpoint,
    transform,
    initialData
  }: {
    endpoint: string,
    transform?: (data: any) => T,
    initialData?: T,
  }
) {
  const abortControllerRef = {current: null as AbortController | null};

  return create<ApiState<T>>((set) => ({
    data: initialData,
    loading: false,
    error: null,

    cancel: () => {
      abortControllerRef.current?.abort();
    },

    fetch: async (params: Record<string, string> = {}) => {
      abortControllerRef.current?.abort();
      abortControllerRef.current = new AbortController();

      set({loading: true, error: null});

      try {
        const queryParams = Object.entries(params).filter(([_, v]) => v != null && v != "")
        const queryString = new URLSearchParams(queryParams).toString();

        const response = await fetch(
          `${endpoint}${queryString ? `?${queryString}` : ''}`,
          {signal: abortControllerRef.current.signal}
        );

        if (!response.ok) {
          throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }

        const json = await response.json();
        const data = transform ? transform(json) : json;

        set({data, loading: false});
      } catch (error) {
        if (error.name !== 'AbortError') {
          set({error: error as Error, loading: false});
        }
      }
    },
  }));
}
