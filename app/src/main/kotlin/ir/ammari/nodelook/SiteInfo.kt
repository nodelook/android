package ir.ammari.nodelook

data class SiteInfo(
    val name: String,
    val url: String,
    val shouldContain: String,
    val address: String,
    val invertMatch: String
)
