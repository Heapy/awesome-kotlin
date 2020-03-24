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

// https://github.com/JetBrains/kotlin-web-site/blob/master/pages/user-groups/user-group-list.md
const val communities = """
### Europe

 * [Aachen Kotlin User Group](https://www.facebook.com/groups/KUGAachen/), Germany
 * [Amsterdam Kotlin User Group](https://www.meetup.com/kotlin-amsterdam/), Netherlands
 * [Amsterdam High-performance Kotlin User Group](https://www.meetup.com/High-performance-Kotlin/), Netherlands
 * [Athens Kotlin User Group](https://www.meetup.com/Kotlin-Athens/), Greece
 * [Barcelona, KotlinBCN](https://www.meetup.com/kotlinbcn/), Spain
 * [Basel Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Basel/), Switzerland
 * [Belarus Kotlin User Group](https://bkug.by/), Belarus
 * [Belfast Kotlin User Group](https://www.meetup.com/kotlin-belfast/), Northern Ireland
 * [Belgium Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Belgium/), Belgium
 * [Berlin Kotlin User Group](https://www.meetup.com/kotlin-berlin/), Germany
 * [Bielefelder Kotlin User Group](https://www.meetup.com/Bielefelder-Kotlin-User-Group/), Germany
 * [Bosnia Kotlin User Group](https://www.facebook.com/kotlinugbosnia), Bosnia and Herzegovina
 * [Brighton Kotlin User Group](https://www.meetup.com/Brighton-Kotlin/), United Kingdom
 * [Bucharest Kotlin User Group](https://www.meetup.com/kug-bucharest/), Romania
 * [Budapest Kotlin User Group](https://www.facebook.com/groups/KotlinBudapest/), Hungary
 * [Bulgarian Kotlin User Group](https://bg-kug.github.io/), Bulgaria
 * [Cologne Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Cologne/?from=ref), Germany
 * [Copenhagen Kotlin User Group](https://www.meetup.com/Kotlin-Copenhagen/), Denmark
 * [Cosenza Kotlin User Group](https://www.facebook.com/groups/251720265392355), Italy
 * [Croatia Kotlin User Group](https://www.meetup.com/Croatia-Kotlin-User-Group-Meetup/), Croatia
 * [Czech Kotlin User Group](https://www.facebook.com/czkug/), Czech Republic
 * [Dnipro Kotlin User Group](https://www.meetup.com/Kotlin-Dnipro/), Ukraine
 * [Dublin Kotlin User Group](https://www.meetup.com/Dublin-Kotlin-Meetup/), Ireland
 * [Dusseldorf Kotlin User Group](https://www.meetup.com/Dusseldorf-Kotlin-Meetup/), Germany
 * [Dutch Kotlin User Group](http://kotlin.nl/), Netherlands
 * [Edinburgh Kotlin User Group](https://www.meetup.com/Edinburgh-Kotlin-User-Group/), Scotland
 * [Estonia Kotlin User Group](https://www.facebook.com/groups/estoniaKotlin/), Estonia
 * [Hamburg Kotlin User Group](https://www.meetup.com/de-DE/Kotlin-User-Group-Hamburg/), Germany
 * [Helsinki Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Helsinki/?from=ref), Finland
 * [Italy Kotlin User Group](https://www.facebook.com/groups/kotlinitaly/), Italy
 * [Karlsruhe Kotlin User Group](https://www.meetup.com/Karlsruhe-Software-Development-Meetup/), Germany
 * [Kassel Kotlin User Group](https://www.meetup.com/Kassel-Kotlin-User-Group/), Germany
 * [Kyiv Kotlin User Group](https://www.meetup.com/KyivKUG), Ukraine
 * [Krakow Kotlin User Group](https://www.meetup.com/krakow-kotlin/), Poland
 * [Leeds Kotlin User Group](https://leedskotlinusergroup.netlify.com/published/2020/1/6/our-first-meetup/), United 
 Kingdom
 * [Limerick Kotlin User Group](https://www.meetup.com/kotlinlimerick/), Ireland
 * [Lisboa Kotlin User Group](https://meetup.com/kotlin-lisboa/), Portugal
 * [London Kotlin](http://www.meetup.com/kotlin-london/), United Kingdom
 * [Lviv Kotlin User Group](https://www.facebook.com/groups/1395212093948927/), Ukraine
 * [Lyon Kotlin User Group](http://www.meetup.com/Lyon-Kotlin-User-Group/), France
 * [Madrid Kotlin User Group](https://www.meetup.com/KotlinMAD/), Spain
 * [Manchester Kotlin Developers](http://www.meetup.com/Kotlin-Manchester/), United Kingdom
 * [Milano Kotlin User Group](https://www.meetup.com/it-IT/KUG-Milan/), Italy
 * [Moscow Kotlin User Group](https://vk.com/kotlinmoscow), Russia
 * [Munich Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Munich/), Germany
 * [Nizhny Novgorod Kotlin User Group](https://www.meetup.com/kotlin_nn/), Russia
 * [Oslo Kotlin User Group](https://www.meetup.com/meetup-group-nWeRbyMu/), Norway
 * [Paris Kotlin User Group](https://www.meetup.com/Kotlin-Paris-Meetup/), France
 * [Rhein-Main Kotlin](https://www.meetup.com/de-DE/Kotlin-Rhein-Main/), Germany
 * [Rostov Kotlin User Group](https://vk.com/rndkotlin), Russia
 * [Samara Kotlin User Group](https://sitc.community/communities/kug/), Russia
 * [St. Petersburg Kotlin User Group](https://www.meetup.com/St-Petersburg-Kotlin-User-Group/), Russia
 * [Serbia Kotlin User Group](https://www.meetup.com/Serbia-Kotlin-User-Group/), Serbia
 * [Slovakia Kotlin User Group](https://www.meetup.com/Kotlin-Slovakia/), Slovakia
 * [Stockholm Kotlin User Group](https://www.meetup.com/Sweden-Kotlin-User-Group/), Sweden
 * [Stuttgart Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Stuttgart/), Germany
 * [Swiss Kotlin User Group](https://www.meetup.com/Kotlin-Swiss-User-Group/), Switzerland
 * [Taganrog Kotlin User Group](https://vk.com/kotlintgn), Russia
 * [Utrecht Kotlin User Group](https://www.meetup.com/meetup-group-YgJEOzCn/), Netherlands
 * [Uzhgorod Kotlin User Group](https://www.facebook.com/groups/135578123824203/), Ukraine
 * [Vienna Kotlin](https://www.meetup.com/Kotlin-Vienna/), Austria
 * [Warsaw Kotlin](https://www.meetup.com/Kotlin-Warsaw/), Poland

### Asia

 * [Ahmedabad Kotlin User Group](https://www.meetup.com/KotlinAhmedabad/), India
 * [Allahabad Kotlin User Group](https://www.facebook.com/Kotlin-Allahabad-User-Group-967463300076405/), India
 * [Astana Java & Kotlin User Group](https://community-z.com/communities/astana-jkug), Kazakhstan
 * [Azerbaijan Kotlin User Group](https://www.facebook.com/groups/395337754167951/), Azerbaijan
 * [Bangladesh Kotlin User Group](https://www.facebook.com/KotlinBangladesh/), Bangladesh
 * [Batam Kotlin User Group](https://t.me/KotlinDevBatam), Indonesia
 * [Bengaluru Kotlin User Group](https://www.facebook.com/KotlinBengaluru/), India
 * [Bengaluru Kotlin 'BlrKotlin' Meetup ](https://www.meetup.com/BlrKotlin/), India
 * [Beijing Kotlin User Group](http://www.kotliner.cn/), China
 * [Bhopal Kotlin User Group](https://www.facebook.com/kotlinbpl/), India
 * [Bhubaneswar Kotlin User Group](https://www.facebook.com/groups/1961143800818624/), India
 * [Cirebon Kotlin User Group](https://t.me/kotlin_crb), Indonesia
 * [Coimbatore Kotlin User Group](https://facebook.com/kotlincbe/), India
 * [Cambodia Kotlin User Group](https://www.facebook.com/groups/kotlinphnompenh), Cambodia
 * [Dubai Kotlin User Group](https://www.facebook.com/kotlindubai/), United Arab Emirates
 * [Jordan Kotlin User Group](https://www.facebook.com/KotlinJO/), Jordan
 * [Hefei Kotlin User Group](http://weibo.com/kotlinhfug), China
 * [Ho Chi Minh Kotlin User Group](https://www.facebook.com/kughcmc/), Vietnam
 * [Hong Kong Kotlin User Group](https://www.facebook.com/KotlinHK), China
 * [Hyderabad Kotlin User Group](https://www.meetup.com/KotlinHyderabad/), India
 * [Indonesia Kotlin User Group](https://www.facebook.com/groups/395469687469099/), Indonesia
 * [Indore Kotlin User Group](https://www.meetup.com/kotlinindore/), India
 * [Israel Kotlin User Group](https://www.facebook.com/groups/107080706530829/), Israel
 * [Istanbul Kotlin User Group](https://www.meetup.com/Kotlin-%C4%B0stanbul/), Turkey
 * [Jalandhar Kotlin User Group](https://nvite.com/JalandharKotlin/13lv7v), India
 * [Japan Kotlin User Group](https://kotlin.connpass.com/), Japan
 * [Karachi Kotlin User Group](https://www.facebook.com/kotlinkarachi/), Pakistan
 * [Kathmandu Kotlin User Group](https://www.facebook.com/groups/100333660782830/), Nepal
 * [Khabarovsk Kotlin User Group](https://devdv.ru/projects/kug), Russia
 * [Kolkata Kotlin User Group](https://www.meetup.com/Kotlin-Kolkata-UG/), India
 * [Korean Kotlin User Group](http://kotlin.kr/), South Korea
 * [Kozhikode Kotlin User Group](https://www.facebook.com/kotlinusergroup/), India
 * [Mumbai Kotlin User Group](https://www.meetup.com/Kotlin-User-Group-Mumbai/), India
 * [Myanmar Kotlin User Group](https://www.facebook.com/groups/kotlinmyanmarusergroup/about/), Myanmar
 * [Nepal Kotlin User Group](https://www.facebook.com/groups/dnkotlin/), Nepal
 * [New Delhi Kotlin User Group](https://www.meetup.com/KotlinDelhi/), India
 * [Pakistan KotlinUser Group](https://www.facebook.com/groups/565405337181251/), Pakistan
 * [Palestine Kotlin User Group](https://www.facebook.com/groups/592717964582711/), State of Palestine
 * [Philippines Kotlin](https://www.facebook.com/groups/642901202586581/), Philippines
 * [Pune Kotlin User Group](https://www.facebook.com/groups/punekotlin), India
 * [Saudi Arabia Kotlin User Group](https://www.facebook.com/KotlinArabia), Saudi Arabia
 * [Shanghai, KotlinThree](http://kotlinthree.github.io/), China
 * [Singapore Kotlin User Group](https://www.meetup.com/Singapore-Kotlin-User-group/), Singapore
 * [Sri Lanka Kotlin User Group](https://www.facebook.com/KotlinSrilanka/), Sri Lanka
 * [Syria Kotlin User Group](https://www.facebook.com/KotlinSyria/), Syria
 * [Surat Kotlin User Group](https://web.facebook.com/kotlinsurat), India
 * [Taiwan Kotlin User Group](https://wetogether.co/kotlin-tw), Taiwan
 * [Tel Aviv Kotlin User Group](https://www.meetup.com/KotlinTLV/), Israel
 * [Thailand Kotlin Developers](https://www.facebook.com/groups/872547279487598/), Thailand
 * [Turkey Kotlin User Group](http://kotlinveandroid.com/), Turkey
 * [Vellore Kotlin User Group](https://www.facebook.com/kotlinvellore), India
 * [Vietnam Kotlin User Group (online community)](https://vnkotlin.com), Vietnam
 * [Vijayawada Kotlin User Group](https://www.facebook.com/KotlinVijayawada/), India

### North America

* [Albuquerque Kotlin User Group](https://www.meetup.com/Kotlin-ABQ/), USA
* [Austin Kotlin User Group](https://www.meetup.com/Austin-Kotlin-Meetup/), USA
* [Brooklyn (NY) Kotlin User Group](https://www.meetup.com/Brooklyn-Kotlin/), USA
* [Cambridge Kotlin Office Hours](https://www.meetup.com/kotlin-office-hours/), USA
* [Chicago Kotlin Users Group](http://www.meetup.com/Chicago-Kotlin/), USA
* [Cincinnati Kotlin User Group](https://www.meetup.com/Cincinnati-Kotlin/), USA
* [Dallas, Kotlin DFW](https://www.meetup.com/Kotlin-DFW/), USA
* [Guadalajara Kotlin User Group](https://www.meetup.com/es/Kotlin-User-Group-GDL/), Mexico
* [Guatemala Kotlin Meetup](https://www.meetup.com/Guatemala-Kotlin-Meetup/), Guatemala
* [Houston Kotlin User Group](https://www.hccug.org/), USA
* [Kansas City User Group](https://www.meetup.com/Kansas-City-Kotlin-User-Group/), USA
* [Mexico, CDMX Kotlin User Group](https://www.meetup.com/es-ES/Kotlin-Nights-CDMX/), Mexico
* [Mérida Kotlin User Group](https://www.meetup.com/Merida-Kotlin-User-Group/), Mexico
* [New England User Group](https://www.meetup.com/New-England-Kotlin-Users-Group/), USA
* [New York Kotlin Meetup](http://www.meetup.com/New-York-Kotlin-Meetup/), USA
* [Norfolk Kotlin User Group](mailto:robert.chrzanowski@gmail.com), USA
* [Irvine Kotlin User Group "OC Kotlin Krew"](https://www.meetup.com/OCKotlinKrew/), USA
* [Portland Kotlin User Group](https://www.meetup.com/Kotlin-Portland-User-Group/), USA
* [San Diego Kotlin User Group](https://www.meetup.com/sd-kotlin/), USA
* [San Francisco Kotlin Meetup](https://www.meetup.com/San-Francisco-Kotlin-Meetup/), USA
* [Santa Cruz Kotlin User Group](https://www.meetup.com/Santa-Cruz-Kotlin-User-Group/events/245895831/), USA
* [South Florida Kotlin User Group](https://www.meetup.com/Kotlin-South-Florida-Users-Group/), USA
* [Toronto Kotlin](https://www.meetup.com/Kotlin-Toronto/events/235740293/), Canada
* [Twin Cities Kotlin User Group](https://www.meetup.com/Twin-Cities-Kotlin-User-Group/), USA
* [Utah Kotlin](https://www.meetup.com/Kotlin-Utah/), USA
* [Vancouver Kotlin Meetup](https://www.meetup.com/VancouverKotlin/), Canada
* [Washington DC Kotlin User Group](https://www.meetup.com/DCKotlin/), USA
* [Waterloo Kotlin P2P](https://www.meetup.com/Kotlin-Waterloo-P2P/events/), Canada
* [West Florida Kotlin User Group](https://www.facebook.com/groups/KotlnWestFlorida), USA

### South America

* [Asunción Kotlin User Group](https://kotlin-user-group-asuncion-py.github.io/), Paraguay
* [Brasil Kotlin User Group](https://groups.google.com/forum/#!forum/kotlin-brasil), Brazil
* [Buenos Aires Desarrollo en Android con Kotlin](https://www.meetup.com/Desarrollo-en-Android-con-Kotlin/), Argentina
* [Cascavel Kotlin Meetup](https://www.meetup.com/Kotlin-Meetup-Cascavel/), Brazil
* [Chile Kotlin User Group](http://www.facebook.com/kotlinchile), Chile
* [Cochabamba Kotlin User Group](https://www.facebook.com/kotlincocha/), Bolivia
* [Curitiba CWB Kotlin User Group](https://www.meetup.com/KotlinCWB/), Brazil
* [El Alto Kotlin User Group](https://m.facebook.com/KotlinElAlto/), Bolivia
* [La Paz Kotlin User Group](https://www.facebook.com/KotlinLaPaz/), Bolivia
* [Lima Kotlin User Group](https://www.facebook.com/groups/limakotlin/), Peru
* [Peru Kotlin User Group](https://www.facebook.com/groups/1540580306247047/), Peru
* [Porto Alegre Kotlin User Group](https://www.meetup.com/Kotlin-RS/), Brazil
* [Santiago Kotlin User Group](https://www.facebook.com/kotlinsantiago/), Chile
* [Sao Paulo Kotlin Meetup](https://www.meetup.com/kotlin-meetup-sp/), Brazil
* [Rio de Janeiro](https://www.meetup.com/Kotlin-Rio/), Brazil

### Australia/Oceania

* [Brisbane Kotlin User Group](https://www.meetup.com/Brisbane-Kotlin-User-Group/), Australia
* [Melbourne Kotlin User Group](https://www.meetup.com/Melbourne-Kotlin-Meetup/), Australia
* [Sydney Kotlin User Group](https://sydspace.org/kotlin/), Australia
* [Wellington Kotlin User Group](https://www.meetup.com/Wellington-kt/), New Zealand

### Africa

* [Abidjan Kotlin User Group](https://www.facebook.com/groups/778398068995641/), Cote d'Ivoire
* [Abuja Kotlin User Group](https://www.meetup.com/Kotlin-Abuja-User-Group-Nigeria/), Nigeria
* [Accra Ghana Kotlin User Group](http://groupspaces.com/AccraGhanaKotlinUserGroup/), Ghana
* [Ado-Ekiti Kotlin User Group](https://web.facebook.com/groups/1598555973529998/?ref=group_header), Nigeria
* [Agadir Kotlin User Group](https://www.meetup.com/Agadir-Kotlin-User-Group/), Morocco
* [Algeria Kotlin User Group](https://web.facebook.com/groups/383147662448461/), Algeria
* [Angola Kotlin User Group](https://www.facebook.com/groups/405177603231134/about/), Angola
* [Bauchi Kotlin User Group](https://www.facebook.com/groups/kotlinbauchi/), Nigeria
* [Beira Kotlin User Group](https://www.facebook.com/groups/470398203308975/), Mozambique
* [Benin Kotlin User Group](https://www.meetup.com/Kotlin-Benin/), Nigeria
* [Brazza Kotlin User Group](https://www.facebook.com/groups/KotlinBrazza/), Congo
* [Cairo Kotlin User Group](https://www.facebook.com/Kotlin.Cairo/), Egypt
* [Cotonou Kotlin User Group](https://www.facebook.com/kotlinCotonou/), Benin
* [Egypt Kotlin](https://www.facebook.com/kotlinegypt/), Egypt
* [Enugu Kotlin User Group](https://kotlin-enuguusergroup.slack.com/), Nigeria
* [Johannesburg Kotlin User Group](https://www.meetup.com/Kotlin-Software-Development-Meetup/), South Africa
* [Kano Kotlin User Group](https://www.meetup.com/Kotlin-Kano-User-Group-Nigeria/), Nigeria
* [Katsina Kotlin User Group](https://www.downtomeet.com/Katsina-Kotlin-Group), Nigeria
* [Lagos Kotlin User Group](https://www.meetup.com/Lagos-Kotlin-Meetup/), Nigeria
* [Lapai Kotlin User Group](https://www.meetup.com/Kotlin-Lapai-User-Group/), Nigeria
* [Lome Kotlin User Group](https://www.facebook.com/groups/1825278611119862/), Togo
* [Lubumbashi Kotlin User Group](https://www.meetup.com/Lubumbashi-Kotlin-User-Group/), Congo
* [Minna Kotlin User Group](https://www.meetup.com/Kotlin-Minna-User-Group-Nigeria/), Nigeria
* [Minya Kotlin User Group](https://www.facebook.com/kotlinminya/), Egypt
* [Nairobi Kotlin User Group](https://www.meetup.com/KotlinKenya/), Kenya
* [Noun Kotlin User Group](https://www.meetup.com/Noun-Kotlin-User-Group/), Cameroon
* [Ondo Kotlin User Group](https://www.meetup.com/Kotlin-Ondo-User-Group/), Nigeria
* [Ouagadougou Kotlin User Group](https://www.facebook.com/groups/649651491892414/), Burkina Faso
* [Rabat Kotlin User Group](https://www.meetup.com/Rabat-Kotlin-User-Group/), Morocco
* [Rivers Kotlin User Group](https://www.facebook.com/groups/2178453949151460/), Nigeria
* [Tunisia Kotlin User Group](https://www.facebook.com/groups/1501353116571104), Tunisia
* [Uganda Kotlin User Group](https://www.facebook.com/Uganda-Kotlin-User-Group-613707548753658/), Uganda
* [Warri Kotlin User Group](https://www.meetup.com/kotlin-warri/), Nigeria
* [Yola Kotlin User Group](https://www.facebook.com/Kotlin-Yola-User-Group-104198534358013/), Nigeria
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

fun main() {
    convertToAwesomeLink()
}
