import * as React from "react";
import {ChangeEvent, KeyboardEvent, useEffect, useRef, useState} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import "./search.less";
import {searchString} from "../../locations";

export function Search(props: SearchProps) {
  const [query, setQuery] = useState("");
  const location = useLocation();
  const navigate = useNavigate();
  const params = useParams();
  const searchInputRef = useRef(null);

  useEffect(() => {
    const q = new URLSearchParams(location.search).get("q");
    if (q) {
      setQuery(q);
      props.onChange(q);

      navigate({
        search: searchString({...params, q}),
      });
    }
    searchInputRef.current.focus();
  }, []);

  function handleChange(event: ChangeEvent<HTMLInputElement>) {
    const q = event.target.value;
    setQuery(q);
    props.onChange(q);
    navigate({
      search: searchString({...params, q})
    });
  }

  return (
    <section className="search">
      <form className="search_wrapper">
        <input className="search_field"
               ref={searchInputRef}
               onKeyPress={handleKeyPress}
               onChange={handleChange}
               placeholder="Type to Filter"
               value={query}/>
      </form>
    </section>
  );
}

function handleKeyPress(event: KeyboardEvent<Element>) {
  if (event.key === "Enter") {
    event.preventDefault();
    return false;
  }
}

interface SearchProps {
  onChange: (value: string) => void;
}
