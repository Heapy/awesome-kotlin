package link.kotlin.scripts

// Отображение статей
// https://tympanus.net/codrops/collective/collective-303/
/**
 * TODO.
 *
 * @author Ibragimov Ruslan
 * @since TODO
 */
val req = """
GET https://mercury.postlight.com/parser?url=https://trackchanges.postlight.com/building-awesome-cms-f034344d8ed
    Content-Type: application/json
    x-api-key: DAMEpPMtHML95VhP0bSC3Xt1fAIH1lgAEMBuiXSV
"""

//https://github.com/giflw/remark-java

val res = """
HTTP/1.0 200 OK
{
  "title": "An Ode to the Rosetta Spacecraft as It Flings Itself Into a Comet",
  "content": "<div><article class="content body-copy"> <p>Today, the European Space Agency’s... ",
  "date_published": "2016-09-30T07:00:12.000Z",
  "lead_image_url": "https://www.wired.com/wp-content/uploads/2016/09/Rosetta_impact-1-1200x630.jpg",
  "dek": "Time to break out the tissues, space fans.",
  "url": "https://www.wired.com/2016/09/ode-rosetta-spacecraft-going-die-comet/",
  "domain": "www.wired.com",
  "excerpt": "Time to break out the tissues, space fans.",
  "word_count": 1031,
  "direction": "ltr",
  "total_pages": 1,
  "rendered_pages": 1,
  "next_page_url": null
}
"""
