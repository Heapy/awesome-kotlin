package link.kotlin.scripts.import

import org.commonmark.node.BulletList
import org.commonmark.node.Document
import org.commonmark.node.Heading
import org.commonmark.node.Link
import org.commonmark.node.ListItem
import org.commonmark.node.Paragraph
import org.commonmark.node.Text
import org.commonmark.parser.Parser
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

const val communities = """
### Europe

 * [Amsterdam Kotlin User Group](https://www.meetup.com/kotlin-amsterdam/), Netherlands
 * [Athens Kotlin User Group](https://www.meetup.com/Kotlin-Athens/), Greece
 * [Barcelona, KotlinBCN](https://www.meetup.com/kotlinbcn/), Spain
 * [Belarus Kotlin User Group](https://bkug.by/), Belarus
 * [Belgium Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Belgium/), Belgium
 * [Berlin Kotlin User Group](https://www.meetup.com/kotlin-berlin/), Germany
 * [Bosnia Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Bosnia/), Bosnia and Herzegovina
 * [Bucharest Kotlin User Group](https://www.meetup.com/kug-bucharest/), Romania
 * [Budapest Kotlin User Group](https://www.facebook.com/groups/KotlinBudapest/), Hungary
 * [Cologne Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Cologne/?from=ref), Germany
 * [Croatia Kotlin User Group](https://www.meetup.com/Croatia-Kotlin-User-Group-Meetup/), Croatia
 * [Czech Kotlin User Group](https://www.facebook.com/czkug/), Czech Republic
 * [Dnipro Kotlin User Group](https://www.meetup.com/Kotlin-Dnipro/), Ukraine
 * [Dublin Kotliners](https://www.meetup.com/Dublin-Kotliners/), Ireland
 * [Dusseldorf Kotlin User Group](https://www.meetup.com/Dusseldorf-Kotlin-Meetup/), Germany
 * [Dutch Kotlin User Group](http://kotlin.nl/), Netherlands
 * [Hamburg Kotlin User Group](https://www.meetup.com/de-DE/Kotlin-User-Group-Hamburg/), Germany
 * [Helsinki Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Helsinki/?from=ref), Finland
 * [Italy Kotlin User Group](https://www.facebook.com/groups/kotlinitaly/), Italy
 * [Karlsruhe Kotlin User Group](https://www.meetup.com/Karlsruhe-Software-Development-Meetup/), Germany
 * [Kyiv Kotlin User Group](https://www.meetup.com/KyivKUG), Ukraine
 * [London Kotlin](http://www.meetup.com/kotlin-london/), United Kingdom
 * [Lyon Kotlin User Group](http://www.meetup.com/Lyon-Kotlin-User-Group/), France
 * [Madrid Kotlin User Group](https://www.meetup.com/KotlinMAD/), Spain
 * [Manchester Kotlin Developers](http://www.meetup.com/Kotlin-Manchester/), United Kingdom
 * [Milano Kotlin User Group](https://www.meetup.com/it-IT/KUG-Milan/), Italy
 * [Moscow Kotlin User Group](https://vk.com/kotlinmoscow), Russia
 * [Munich Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Munich/), Germany
 * [Paris Kotlin User Group](https://www.meetup.com/Kotlin-Paris-Meetup/), France
 * [Rhein-Main Kotlin](https://www.meetup.com/de-DE/Kotlin-Rhein-Main/), Germany
 * [St. Petersburg Kotlin User Group](https://www.meetup.com/St-Petersburg-Kotlin-User-Group/), Russia
 * [Serbia Kotlin User Group](https://www.meetup.com/Serbia-Kotlin-User-Group/), Serbia
 * [Stockholm Kotlin User Group](https://www.meetup.com/Sweden-Kotlin-User-Group/), Sweden
 * [Swiss Kotlin User Group](https://www.meetup.com/Kotlin-Swiss-User-Group/), Swiss
 * [Toulouse Kotlin User Group](https://www.meetup.com/fr-FR/Toulouse-Kotlin-User-Group/), France
 * [Utrecht Kotlin User Group](https://www.meetup.com/meetup-group-YgJEOzCn/), Netherlands
 * [Uzhgorod Kotlin User Group](https://www.facebook.com/groups/135578123824203/), Ukraine
 * [Vienna Kotlin](https://www.meetup.com/Kotlin-Vienna/), Austria
 * [Wroclaw Kotlin](https://www.meetup.com/pl-PL/Kotlin-Wroclaw), Poland
 * [Warsaw Kotlin](https://www.meetup.com/Kotlin-Warsaw/), Poland
 * [Yorkshire Kotlin Meetup](http://www.meetup.com/Kotlin-Yorkshire-Meetup-Group/), United Kingdom


### Asia

 * [Ahmedabad Kotlin User Group](https://www.meetup.com/KotlinAhmedabad/), India
 * [Allahabad Kotlin User Group](https://www.facebook.com/Kotlin-Allahabad-User-Group-967463300076405/), India
 * [Azerbaijan Kotlin User Group](https://www.facebook.com/groups/395337754167951/), Azerbaijan
 * [Bangladesh Kotlin User Group](https://www.facebook.com/KotlinBangladesh/), Bangladesh
 * [Bengaluru Kotlin User Group](https://www.facebook.com/KotlinBengaluru/), India
 * [Bengaluru Kotlin 'BlrKotlin' Meetup ](https://www.meetup.com/BlrKotlin/), India
 * [Beijing Kotlin User Group](http://www.kotliner.cn/), China
 * [Bhopal Kotlin User Group](https://www.facebook.com/kotlinbpl/), India
 * [Bhubaneswar Kotlin User Group](https://www.facebook.com/groups/1961143800818624/), India
 * [Chennai Kotlin User Group](https://www.meetup.com/Chennai-Kotlin-User-Group/), India
 * [Chengdu Kotlin User Group](https://www.kotliner.cn/chengdu/), China
 * [Coimbatore Kotlin User Group](https://facebook.com/kotlincbe/), India
 * [Cambodia Kotlin User Group](https://www.facebook.com/groups/kotlinphnompenh), Cambodia
 * [Dubai Kotlin User Group](https://www.facebook.com/kotlindubai/), United Arab Emirates
 * [Hefei Kotlin User Group](http://weibo.com/kotlinhfug), China
 * [Hyderabad Kotlin User Group](https://www.facebook.com/KotlinHyd/), India
 * [Indonesia Kotlin User Group](https://www.facebook.com/groups/395469687469099/), Indonesia
 * [Indore Kotlin User Group](https://www.meetup.com/kotlinindore/), India
 * [Israel Kotlin User Group](https://www.facebook.com/groups/107080706530829/), Israel
 * [Istanbul Kotlin User Group](https://www.meetup.com/Kotlin-%C4%B0stanbul/), Turkey
 * [Jalandhar Kotlin User Group](https://nvite.com/JalandharKotlin/13lv7v), India
 * [Japan Kotlin User Group](https://kotlin.connpass.com/), Japan
 * [Karachi Kotlin User Group](https://www.facebook.com/kotlinkarachi/), Pakistan
 * [Kathmandu Kotlin User Group](https://www.facebook.com/groups/100333660782830/), Nepal
 * [Kolkata Kotlin User Group](https://www.meetup.com/Kotlin-Kolkata-UG/), India
 * [Korean Kotlin User Group](http://kotlin.kr/), Korea
 * [Kozhikode Kotlin User Group](https://www.facebook.com/kotlinusergroup/), India
 * [Mumbai Kotlin User Group](https://www.facebook.com/kotlinmumbai/), India
 * [Mumbai Kotlin for Android](https://www.meetup.com/Kotline-for-Android/), India
 * [Myanmar Kotlin User Group](https://www.facebook.com/groups/kotlinmyanmarusergroup/about/), Myanmar
 * [Nepal Kotlin User Group](https://www.facebook.com/groups/dnkotlin/), Nepal
 * [New Delhi Kotlin User Group](https://www.facebook.com/kotlinNewDelhi/), India
 * [Pakistan KotlinUser Group](https://www.facebook.com/groups/565405337181251/), Pakistan
 * [Philippines Kotlin](https://www.facebook.com/groups/642901202586581/), Philippines
 * [Pune Kotlin User Group](https://www.facebook.com/groups/punekotlin), India
 * [Saudi Arabia Kotlin User Group](https://www.facebook.com/KotlinArabia), Saudi Arabia
 * [Shanghai, KotlinThree](http://kotlinthree.github.io/), China
 * [Singapore Kotlin User Group](https://www.meetup.com/Singapore-Kotlin-User-group/), Singapore
 * [Sri Lanka Kotlin User Group](https://www.facebook.com/KotlinSrilanka/), Sri Lanka
 * [Syria Kotlin User Group](https://www.facebook.com/KotlinSyria/), Syria
 * [Sultanpur KNIT Kotlin User Group](https://www.facebook.com/KotlinKnit/), India
 * [Taiwan Kotlin User Group](https://wetogether.co/kotlin-tw), Taiwan
 * [Tel Aviv Kotlin User Group](https://www.meetup.com/KotlinTLV/), Israel
 * [Thailand Kotlin Developers](https://www.facebook.com/groups/872547279487598/), Thailand
 * [Turkey Kotlin User Group](http://kotlinveandroid.com/), Turkey
 * [Vellore Kotlin User Group](https://www.facebook.com/kotlinvellore), India
 * [Vietnam Kotlin User Group](https://vnkotlin.com), Vietnam
 * [Vijayawada Kotlin User Group](https://www.facebook.com/KotlinVijayawada/), India

</div>

<div class="g-6" markdown="1">

### North America

* [Albuquerque Kotlin User Group](https://www.meetup.com/Kotlin-ABQ/), USA
* [Bay Area Kotlin User Group](http://www.meetup.com/Bay-Area-Kotlin-User-Group/), USA
* [Brooklyn (NY) Kotlin User Group](https://www.meetup.com/Brooklyn-Kotlin/), USA
* [Cambridge Kotlin Office Hours](https://www.meetup.com/kotlin-office-hours/), USA
* [Chicago Kotlin Users Group](http://www.meetup.com/Chicago-Kotlin/), USA
* [Cincinnati Kotlin User Group](https://www.meetup.com/Cincinnati-Kotlin/), USA
* [Columbus Kotlin User Group](https://www.meetup.com/Columbus-Kotlin-User-Group/), USA
* [Dallas, Kotlin DFW](https://www.meetup.com/Kotlin-DFW/), USA
* [Guadalajara Kotlin User Group](https://www.meetup.com/es/Kotlin-User-Group-GDL/), Mexico
* [Guatemala Kotlin Meetup](https://www.meetup.com/Guatemala-Kotlin-Meetup/), Guatemala
* [Mexico, CDMX Kotlin User Group](https://www.meetup.com/es-ES/Kotlin-Nights-CDMX/), Mexico
* [Mérida Kotlin User Group](https://www.meetup.com/Merida-Kotlin-User-Group/), Mexico
* [New England User Group](https://www.meetup.com/New-England-Kotlin-Users-Group/), USA
* [New York Kotlin Meetup](http://www.meetup.com/New-York-Kotlin-Meetup/), USA
* [Norfolk Kotlin User Group](mailto:robert.chrzanowski@gmail.com), USA
* [Orange County Kotlin Meetup](https://www.meetup.com/oc-kotlin-meetup/), USA
* [San Diego Kotlin User Group](https://www.meetup.com/sd-kotlin/), USA
* [Santa Cruz Kotlin User Group](https://www.meetup.com/Santa-Cruz-Kotlin-User-Group/events/245895831/), USA
* [South Florida Kotlin User Group](https://www.meetup.com/Kotlin-South-Florida-Users-Group/), USA
* [Toronto Kotlin](https://www.meetup.com/Kotlin-Toronto/events/235740293/), Canada
* [Twin Cities Kotlin User Group](https://www.meetup.com/Twin-Cities-Kotlin-User-Group/), USA
* [Utah Kotlin](https://www.meetup.com/Kotlin-Utah/), USA
* [Vancouver Kotlin Meetup](https://www.meetup.com/VancouverKotlin/), Canada
* [Washington DC Kotlin User Group](https://www.meetup.com/DCKotlin/), USA
* [West Florida Kotlin User Group](https://www.facebook.com/groups/KotlnWestFlorida), USA

### South America

* [Brasil Kotlin User Group](https://groups.google.com/forum/#!forum/kotlin-brasil), Brazil
* [Buenos Aires Desarrollo en Android con Kotlin](https://www.meetup.com/Desarrollo-en-Android-con-Kotlin/), Argentina
* [Cascavel Kotlin Meetup](https://www.meetup.com/Kotlin-Meetup-Cascavel/), Brazil
* [Chile Kotlin User Group](http://www.facebook.com/kotlinchile), Chile
* [Cochabamba Kotlin User Group](https://www.facebook.com/kotlincocha/), Bolivia
* [El Alto Kotlin User Group](https://www.meetup.com/es/Kotlin-El-Alto/?_cookie-check=x7g06koKCyQaUPnm), Bolivia
* [La Paz Kotlin User Group](https://www.facebook.com/KotlinLaPaz/), Bolivia
* [Lima Kotlin User Group](https://www.facebook.com/groups/limakotlin/), Peru
* [Peru Kotlin User Group](https://www.facebook.com/groups/1540580306247047/), Peru
* [Sao Paulo Kotlin Meetup](https://www.meetup.com/kotlin-meetup-sp/), Brazil
* [Rio de Janeiro](https://www.meetup.com/Kotlin-Rio/), Brazil

### Australia/Oceania

* [Brisbane Kotlin User Group](https://www.meetup.com/Brisbane-Kotlin-User-Group/), Australia
* [Sydney Kotlin User Group](https://sydkotlin.space/), Australia
* [Wellington Kotlin User Group](https://www.meetup.com/Wellington-kt/), New Zealand

### Africa

* [Abidjan Kotlin User Group](https://www.facebook.com/groups/778398068995641/), Cote d'Ivoire
* [Abuja Kotlin User Group](https://www.meetup.com/Kotlin-Abuja-User-Group-Nigeria/), Nigeria
* [Agadir Kotlin User Group](https://www.meetup.com/Agadir-Kotlin-User-Group/), Morocco
* [Ado-Ekiti Kotlin User Group](https://web.facebook.com/groups/1598555973529998/?ref=group_header), Nigeria
* [Angola Kotlin User Group](https://www.facebook.com/groups/405177603231134/about/), Angola
* [Beira Kotlin User Group](https://www.facebook.com/groups/470398203308975/), Mozambique
* [Brazza Kotlin User Group](https://www.facebook.com/groups/KotlinBrazza/), Congo
* [Cairo Kotlin User Group](https://www.facebook.com/Kotlin.Cairo/), Egypt
* [Cotonou Kotlin User Group](https://www.facebook.com/kotlinCotonou/), Benin
* [Egypt Kotlin](https://www.facebook.com/kotlinegypt/), Egypt
* [Fayoum Kotlin User Group](https://www.facebook.com/kdgfayoum/), Egypt
* [Johannesburg Kotlin User Group](https://www.meetup.com/Kotlin-Software-Development-Meetup/), South Africa
* [Lagos Kotlin User Group](https://www.meetup.com/Lagos-Kotlin-Meetup/), Nigeria
* [Lome Kotlin User Group](https://www.facebook.com/groups/1825278611119862/), Togo
* [Minya Kotlin User Group](https://www.facebook.com/kotlinminya/), Egypt
* [Nairobi Kotlin User Group](https://www.meetup.com/KotlinKenya/), Kenya
* [Noun Kotlin User Group](https://www.meetup.com/Noun-Kotlin-User-Group/), Cameroon
* [Ondo Kotlin User Group](https://www.meetup.com/Kotlin-Ondo-User-Group/), Nigeria
* [Ouagadougou Kotlin User Group](https://www.facebook.com/groups/649651491892414/), Burkina Faso
* [Rabat Kotlin User Group](https://www.meetup.com/Rabat-Kotlin-User-Group/), Morocco
* [Tunisia Kotlin User Group](https://www.facebook.com/groups/1501353116571104), Tunisia
* [Uganda Kotlin User Group](https://www.facebook.com/Uganda-Kotlin-User-Group-613707548753658/), Uganda
"""

fun convertToAwesomeLink() {
    val node = Parser.builder().build().parse(communities)

    if (node is Document) {
        var child = node.firstChild

        val process = mutableMapOf<Heading, BulletList>()

        while (true) {
            if (child is Heading) {
                val next = child.next
                if (next is BulletList) {
                    process[child] = next
                }
            }

            child = child.next ?: break
        }

        val categoryBuilder = StringBuilder()

        categoryBuilder.append("""
            import link.kotlin.scripts.LinkType.kug
            import link.kotlin.scripts.Tags
            import link.kotlin.scripts.category
            import link.kotlin.scripts.link
            import link.kotlin.scripts.subcategory

            category("Kotlin User Groups") {
        """.trimIndent())
        categoryBuilder.append("\n")

        process.forEach {
            val text = it.key.firstChild
            val subcategory = (text as Text).literal

            val subcategoryBuilder = buildString {
                append("""  subcategory("$subcategory") {""")
                append("\n")

                var item = it.value.firstChild
                while (true) {
                    val p = ((item as ListItem).firstChild as Paragraph)
                    val a = (p.firstChild as Link)
                    val ah = a.destination
                    val at = (a.firstChild as Text).literal
                    val t = (p.lastChild as Text).literal.removePrefix(", ")
                    val link = """
                   |    link {
                   |      name = "$at"
                   |      desc = "$t"
                   |      href = "$ah"
                   |      type = kug
                   |      tags = Tags["$t"]
                   |    }
                   |
                """.trimMargin()

                    append(link)
                    item = item.next ?: break
                }
                append("  }\n")
            }

            categoryBuilder.append(subcategoryBuilder)
        }

        categoryBuilder.append("}")
        categoryBuilder.append("\n")

        Files.write(Paths.get("./src/main/resources/links/UserGroups.kts"), categoryBuilder.toString().toByteArray(Charsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
    }

    println("Done")
}

fun main(args: Array<String>) {
    convertToAwesomeLink()
}
