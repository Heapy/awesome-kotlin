package link.kotlin.scripts.data

import link.kotlin.scripts.LinkType.kug

val userGroups = category("Kotlin User Groups") {
  subcategory("Europe") {
    link {
      name = "Kotlin Developers in Manchester"
      desc = "United Kingdom"
      href = "http://www.meetup.com/Kotlin-Manchester/"
      type = kug
      tags = arrayOf("United Kingdom")
    }
    link {
      name = "Belarus Kotlin User Group"
      desc = "Belarus"
      href = "https://bkug.by/"
      type = kug
      tags = arrayOf("Belarus")
    }
    link {
      name = "Kotlin User Group Munich"
      desc = "Deutschland"
      href = "http://www.meetup.com/de-DE/Kotlin-User-Group-Munich/"
      type = kug
      tags = arrayOf("Deutschland")
    }
    link {
      name = "Lyon Kotlin User Group"
      desc = "France"
      href = "http://www.meetup.com/Lyon-Kotlin-User-Group/"
      type = kug
      tags = arrayOf("France")
    }
    link {
      name = "KotlinMAD"
      desc = "Spain"
      href = "https://www.meetup.com/KotlinMAD/"
      type = kug
      tags = arrayOf("Spain")
    }
    link {
      name = "Kotlin Yorkshire Meetup Group"
      desc = "United Kingdom"
      href = "http://www.meetup.com/Kotlin-Yorkshire-Meetup-Group/"
      type = kug
      tags = arrayOf("United Kingdom")
    }
    link {
      name = "Kotlin London"
      desc = "United Kingdom"
      href = "http://www.meetup.com/kotlin-london/"
      type = kug
      tags = arrayOf("United Kingdom")
    }
    link {
      name = "Kotlin User Group Berlin"
      desc = "Germany"
      href = "https://www.meetup.com/kotlin-berlin/"
      type = kug
      tags = arrayOf("Germany")
    }
  }
  subcategory("America") {
    link {
      name = "Bay Area Kotlin User Group"
      desc = "USA"
      href = "http://www.meetup.com/Bay-Area-Kotlin-User-Group/"
      type = kug
      tags = arrayOf("USA")
    }
    link {
      name = "Chicago Kotlin Users Group"
      desc = "USA"
      href = "https://www.meetup.com/Chicago-Kotlin/"
      type = kug
      tags = arrayOf("USA")
    }
    link {
      name = "Kotlin Group of Boulder"
      desc = "USA"
      href = "http://www.meetup.com/Kotlin-Group-Boulder/"
      type = kug
      tags = arrayOf("USA")
    }
    link {
      name = "New York Kotlin Meetup"
      desc = "USA"
      href = "http://www.meetup.com/New-York-Kotlin-Meetup/"
      type = kug
      tags = arrayOf("USA")
    }
  }
  subcategory("Asia") {
    link {
      name = "Japan Kotlin User Group"
      desc = "Japan"
      href = "https://kotlin.doorkeeper.jp/"
      type = kug
      tags = arrayOf("Japan")
    }
    link {
      name = "Korean Kotlin User Group"
      desc = "Korea"
      href = "http://kotlin.kr/"
      type = kug
      tags = arrayOf("Korea")
    }
  }
  subcategory("Australia") {
    link {
      name = "Brisbane Kotlin User Group"
      desc = "Australia"
      href = "https://www.meetup.com/Brisbane-Kotlin-User-Group/"
      type = kug
      tags = arrayOf("Australia")
    }
  }
}
