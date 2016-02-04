const data = [{
    name: "Links",
    subcategories: [{
        name: "Official Links",
        links: [{
            name: 'Home Page',
            href: 'http://kotlinlang.org/'
        }]
    }, {
        name: "Resources",
        links: [{
            name: '/r/Kotlin',
            href: 'https://www.reddit.com/r/Kotlin/'
        }]
    }]
}, {
    name: "Libraries",
    subcategories: [{
        name: "Android",
        links: [{
            name: 'JetBrains/anko',
            desc: 'Pleasant Android application development.',
            href: 'https://github.com/JetBrains/anko',
            type: 'github',
            star: '2075'
        }]
    }, {
        name: "Web",
        links: [{
            name: 'TinyMission/kara',
            desc: 'Web framework written in Kotlin.',
            href: 'https://github.com/TinyMission/kara',
            type: 'github',
            star: '177'
        }]
    }]
}];

export default data;