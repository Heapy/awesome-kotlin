export interface Logo {
  readonly src: string;
  readonly alt: () => string;
  readonly show: () => boolean;
  readonly exclusive: boolean
  readonly link?: string;
}

export const LOGOS: Logo[] = [
  {
    src: require("./kotlin-14-event.png"),
    alt: () => "Kotlin 1.4 Online Event",
    show: () => {
      const now = Date.now()

      return (1601326800000 <= now) && (now <= 1602786600000);
    },
    exclusive: true,
    link: "https://kotl.in/14event_media?utm_source=awesome_kotlin"
  },
  {
    src: require("./kotlin-force.svg"),
    alt: () => "Star Wars Day",
    show: () => {
      const today = new Date();

      return today.getMonth() === 4 && today.getDate() === 4;
    },
    exclusive: true,
  },
  {
    src: require("./kotlin-birthday.svg"),
    alt: () => {
      return `Kotlin turns ${new Date().getFullYear() - 2011} today!`
    },
    show: () => {
      const today = new Date();

      return today.getMonth() === 6 && today.getDate() === 22;
    },
    exclusive: true,
  },
  {
    src: require("./kotlin-link-birthday.svg"),
    alt: () => {
      return `Awesome Kotlin turns ${new Date().getFullYear() - 2015} today!`
    },
    show: () => {
      const today = new Date();

      return today.getMonth() === 7 && today.getDate() === 18;
    },
    exclusive: true,
  },
  {
    src: require("./kotlin-0.svg"),
    alt: () => "Kotlin Language Logo",
    show: () => true,
    exclusive: false,
  },
  {
    src: require("./kotlin-1.svg"),
    alt: () => "Kotlin Language Logo",
    show: () => true,
    exclusive: false,
  },
  {
    src: require("./kotlin-2.png"),
    alt: () => "Kotlin Language Logo",
    show: () => true,
    exclusive: false,
  }
]
