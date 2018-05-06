import link.kotlin.scripts.LinkType.kug
import link.kotlin.scripts.Tags
import link.kotlin.scripts.category
import link.kotlin.scripts.link
import link.kotlin.scripts.subcategory

/**
 * Instead of deleting links (since they become 404, etc) move them here.
 */
category("Archive") {
    subcategory("Kotlin User Group") {
        link {
            name = "China Kotlin User Group"
            desc = "China"
            href = "http://kotlin.cn/"
            type = kug
            tags = Tags["China"]
        }
        link {
            name = "Japan Kotlin User Group"
            desc = "Japan"
            href = "https://kotlin.doorkeeper.jp/"
            type = kug
            tags = Tags["Japan"]
        }
        link {
            name = "Kotlin Group of Boulder"
            desc = "USA"
            href = "http://www.meetup.com/Kotlin-Group-Boulder/"
            type = kug
            tags = Tags["USA"]
        }
        link {
            name = "Czech Kotlin User Group"
            desc = "Czechia"
            href = "http://kotliners.cz/"
            type = kug
            tags = Tags["Czechia", "Czech"]
        }
        link {
            name = "Kotlin.es"
            desc = "Spain"
            href = "http://kotlin.es/"
            type = kug
            tags = Tags["Spain"]
        }
    }
}
