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
  },
  {
    src: require("./kotlin-3.svg"),
    alt: () => "Kotlin Language Logo",
    show: () => true,
    exclusive: false,
  },
  {
    src: require("./10K.svg"),
    alt: () => {
      return `Celebrating 10 Years of Kotlin!`
    },
    show: () => {
      const now = Date.now()

      return (1623448800000 <= now) && (now <= 1624140000000);
    },
    exclusive: true,
    link: "https://blog.jetbrains.com/kotlin/2021/05/10-years-of-kotlin-stories/"
  },
  {
    src: (() => {
      const maskots = [
        require('./maskots/01.svg'),
        require('./maskots/02.svg'),
        require('./maskots/03.svg'),
        require('./maskots/04.svg'),
        require('./maskots/05.svg'),
        require('./maskots/06.svg'),
        require('./maskots/07.svg'),
        require('./maskots/08.svg'),
        require('./maskots/09.svg'),
        require('./maskots/10.svg'),
        require('./maskots/11.svg'),
        require('./maskots/12.svg'),
        require('./maskots/13.svg'),
        require('./maskots/14.svg'),
      ]

      return maskots[Math.floor(Math.random() * maskots.length)]
    })(),
    alt: () => {
      return `Let’s choose the name for the Kotlin mascot together!`
    },
    show: () => {
      const now = new Date(Date.now())

      return (new Date(2021, 9, 22) <= now) && (now <= new Date(2021, 10, 22));
    },
    exclusive: true,
    link: "https://forms.gle/yktEz3xCAwKEm9cx9"
  },
  {
    src: require("./ukraine.svg"),
    alt: () => "War in Ukraine",
    show: () => {
      const now = Date.now()

      return 1645675200000 <= now; // Until further notification
    },
    exclusive: true,
    link: "https://bank.gov.ua/en/about/support-the-armed-forces"
  },
]
