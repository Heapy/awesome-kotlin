import * as React from "react";
import useNavigationStore from "../store/useNavigationStore";

const NavigationComponent: React.FC = () => {
  const { push, pop } = useNavigationStore((state) => ({
    push: state.push,
    pop: state.pop,
  }));

  return (
    <div>
      <button onClick={() => push("/")}>Go to Home</button>
      <button onClick={() => push("/login")}>Go to About</button>
      <button onClick={pop}>Go Back</button>
    </div>
  );
};

export default NavigationComponent;
