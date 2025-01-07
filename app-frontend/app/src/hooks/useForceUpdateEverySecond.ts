import { useState, useEffect } from "react";

const useForceUpdateEverySecond = () => {
  const [, setTick] = useState(0);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setTick(tick => tick + 1);
    }, 1000);

    return () => clearInterval(intervalId);
  }, []);
};

export default useForceUpdateEverySecond;
