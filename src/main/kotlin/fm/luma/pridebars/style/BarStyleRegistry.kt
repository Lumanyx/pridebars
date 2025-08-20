package fm.luma.pridebars.style

import com.jetbrains.rd.util.first

object BarStyleRegistry {

    private val _styles: HashMap<String, Entry> = HashMap()

    data class Entry(val id: String, val name: String, val style: IGradientBarStyle)

    init {
        register("pride", "Pride", PRIDE_FLAG_STYLE)
        register("trans", "Transgender", TRANS_FLAG_STYLE)
        register("trans_alternative", "Transgender (alternative color scheme)", TRANS_ALTERNATIVE_FLAG_STYLE)
        register("pan", "Pansexual", PAN_FLAG_STYLE)
        register("bisexual", "Bisexual", BISEXUAL_FLAG_STYLE)
        register("lesbian", "Lesbian", LESBIAN_FLAG_STYLE)
        register("gay", "Gay", GAY_FLAG_STYLE)
        register("aromantic", "Aromantic", AROMANTIC_FLAG_STYLE)
        register("aromantic_asexual", "Aromantic Asexual", AROMANTIC_ASEXUAL_FLAG_STYLE)
        register("omnisexual", "Omnisexual", OMNISEXUAL_FLAG_STYLE)
        register("polysexual", "Polysexual", POLYSEXUAL_FLAG_STYLE)
        register("non_binary", "Non-binary", NONBINARY_FLAG_STYLE)
        register("asexual", "Asexual", ASEXUAL_FLAG_STYLE)
    }

    private fun register(id: String, displayName: String, style: IGradientBarStyle) {
        _styles[id] = Entry(id, displayName, style)
    }

    fun getStyle(name: String): IGradientBarStyle {
        var entry = _styles[name]
        if (entry == null) {
            entry = _styles.first().value
        }
        return entry.style
    }

    fun getStyles(): List<Entry> {
        return _styles.values.toList()
    }

    fun getStyleIndex(style: String): Int {
        val styles = getStyles()
        for (index in styles.indices) {
            if (styles[index].id == style) return index
        }
        return 0
    }

}