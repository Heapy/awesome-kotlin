import * as React from "react";
import {useEffect} from "react";
import useNavigationStore from "../../store/useNavigationStore";
import "./search.scss";
import useFocus from "../../hooks/useFocus";

interface SearchProps {
  onChange: (value: string) => void;
}

export function Search(props: SearchProps) {
  const inputRef = useFocus();

  const queryParams = useNavigationStore(state => state.queryParams);
  const setQueryParam = useNavigationStore(state => state.setQueryParam);

  useEffect(() => {
    const q = queryParams.q || "";
    if (q) {
      props.onChange(q);
    }
  }, [queryParams, props]);

  function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    const q = event.target.value;
    setQueryParam("q", q);
    props.onChange(q);
  }

  return (
    <section className="search">
      <form
        className="search_wrapper"
        onSubmit={onFormSubmit}
      >
        <input
          className="search_field"
          ref={inputRef}
          onChange={handleChange}
          placeholder="Type to Filter"
          value={queryParams.q || ""}
        />
      </form>
    </section>
  );
}

function onFormSubmit(event: React.FormEvent) {
  return event.preventDefault();
}
