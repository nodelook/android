package ir.ammari.nodelook

data class Category(
    val title: String,
    val description: String,
    val color: Int,
    val items: List<SiteInfo>,
)
