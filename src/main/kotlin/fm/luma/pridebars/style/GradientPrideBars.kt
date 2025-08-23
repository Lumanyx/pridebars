@file:Suppress("UseJBColor", "InspectionUsingGrayColors")

package fm.luma.pridebars.style

import java.awt.Color

fun Color.multiply(factor: Float): Color {
    return Color(
        (red.toFloat() * factor).toInt().coerceIn(0, 255),
        (green.toFloat() * factor).toInt().coerceIn(0, 255),
        (blue.toFloat() * factor).toInt().coerceIn(0, 255),
    )
}


val TRANS_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(189, 233, 255),
        Color(150, 220, 255),
        Color(56, 189, 255),
        Color(240, 165, 180),
        Color(227, 157, 171),
        Color(217, 150, 163),
        Color(232, 232, 232),
        Color(242, 242, 242),
        Color(250, 250, 250),
        Color(240, 165, 180),
        Color(227, 157, 171),
        Color(217, 150, 163),
        Color(120, 210, 255),
        Color(56, 189, 255),
        Color(18, 136, 196)
    )
)

val TRANS_ALTERNATIVE_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(67, 141, 251).multiply(1.1F),
        Color(67, 141, 251),
        Color(67, 141, 251).multiply(0.9F),
        Color(253, 134, 233).multiply(1.1F),
        Color(253, 134, 233),
        Color(253, 134, 233).multiply(0.9F),
        Color(235, 235, 235).multiply(1.1F),
        Color(235, 235, 235),
        Color(235, 235, 235).multiply(0.9F),
        Color(253, 134, 233).multiply(1.1F),
        Color(253, 134, 233),
        Color(253, 134, 233).multiply(0.8F),
        Color(67, 141, 251).multiply(1.1F),
        Color(67, 141, 251),
        Color(67, 141, 251).multiply(0.5F)
    )
)

val LESBIAN_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(250, 60, 70).multiply(1.1F),
        Color(250, 60, 70),
        Color(250, 60, 70).multiply(0.9F),
        Color(250, 149, 60).multiply(1.1F),
        Color(250, 149, 60),
        Color(250, 149, 60).multiply(0.9F),
        Color(235, 235, 235).multiply(1.1F),
        Color(235, 235, 235),
        Color(235, 235, 235).multiply(0.9F),
        Color(192, 46, 138).multiply(1.1F),
        Color(192, 46, 138),
        Color(192, 46, 138).multiply(0.8F),
        Color(114, 0, 70).multiply(1.05F),
        Color(114, 0, 70),
        Color(114, 0, 70).multiply(0.8F)
    )
)

val PAN_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(237, 0, 150).multiply(1.1F),
        Color(237, 0, 150),
        Color(237, 0, 150).multiply(0.9F),
        Color(254, 195, 9).multiply(1.1F),
        Color(254, 195, 9),
        Color(254, 195, 9).multiply(0.9F),
        Color(67, 141, 251).multiply(1.1F),
        Color(67, 141, 251),
        Color(67, 141, 251).multiply(0.8F),
    )
)

val PRIDE_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(250, 60, 70).multiply(1.1F),
        Color(250, 60, 70),
        Color(250, 60, 70).multiply(0.9F),
        Color(250, 149, 60).multiply(1.1F),
        Color(250, 149, 60),
        Color(250, 149, 60).multiply(0.9F),
        Color(254, 195, 9).multiply(1.1F),
        Color(254, 195, 9),
        Color(254, 195, 9).multiply(0.9F),
        Color(86, 159, 4).multiply(1.1F),
        Color(86, 159, 4),
        Color(86, 159, 4).multiply(0.9F),
        Color(18, 80, 173).multiply(1.1F),
        Color(18, 80, 173),
        Color(18, 80, 173).multiply(0.9F),
        Color(154, 18, 169).multiply(1.1F),
        Color(154, 18, 169),
        Color(154, 18, 169).multiply(0.8F),
    )
)

val GAY_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(54, 101, 80).multiply(1.1F),
        Color(54, 101, 80),
        Color(54, 101, 80).multiply(0.9F),
        Color(97, 171, 137).multiply(1.1F),
        Color(97, 171, 137),
        Color(97, 171, 137).multiply(0.9F),
        Color(235, 235, 235).multiply(1.1F),
        Color(235, 235, 235),
        Color(235, 235, 235).multiply(0.9F),
        Color(107, 152, 218).multiply(1.1F),
        Color(107, 152, 218),
        Color(107, 152, 218).multiply(0.9F),
        Color(18, 80, 173).multiply(1.1F),
        Color(18, 80, 173),
        Color(18, 80, 173).multiply(0.8F),
    )
)

val AROMANTIC_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(86, 159, 4).multiply(1.1F),
        Color(86, 159, 4),
        Color(86, 159, 4).multiply(0.9F),
        Color(161, 202, 114).multiply(1.1F),
        Color(161, 202, 114),
        Color(161, 202, 114).multiply(0.9F),
        Color(235, 235, 235).multiply(1.1F),
        Color(235, 235, 235),
        Color(235, 235, 235).multiply(0.9F),
        Color(102, 102, 102).multiply(1.1F),
        Color(102, 102, 102),
        Color(102, 102, 102).multiply(0.9F),
        Color(38, 38, 38).multiply(1.1F),
        Color(38, 38, 38),
        Color(38, 38, 38).multiply(0.8F),
    )
)

val AROMANTIC_ASEXUAL_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(250, 149, 60).multiply(1.1F),
        Color(250, 149, 60),
        Color(250, 149, 60).multiply(0.9F),
        Color(254, 195, 9).multiply(1.1F),
        Color(254, 195, 9),
        Color(254, 195, 9).multiply(0.9F),
        Color(235, 235, 235).multiply(1.1F),
        Color(235, 235, 235),
        Color(235, 235, 235).multiply(0.9F),
        Color(113, 166, 241).multiply(1.1F),
        Color(113, 166, 241),
        Color(113, 166, 241).multiply(0.9F),
        Color(11, 51, 110).multiply(1.1F),
        Color(11, 51, 110),
        Color(11, 51, 110).multiply(0.8F),
    )
)

val OMNISEXUAL_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(255, 176, 215).multiply(1.1F),
        Color(255, 176, 215),
        Color(255, 176, 215).multiply(0.9F),
        Color(255, 112, 203).multiply(1.1F),
        Color(255, 112, 203),
        Color(255, 112, 203).multiply(0.9F),
        Color(43, 11, 86).multiply(1.1F),
        Color(43, 11, 86),
        Color(43, 11, 86).multiply(0.9F),
        Color(90, 90, 255).multiply(1.1F),
        Color(90, 90, 255),
        Color(90, 90, 255).multiply(0.9F),
        Color(113, 166, 241).multiply(1.1F),
        Color(113, 166, 241),
        Color(113, 166, 241).multiply(0.8F),
    )
)

val BISEXUAL_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(199, 0, 123).multiply(1.1F),
        Color(199, 0, 123),
        Color(199, 0, 123),
        Color(199, 0, 123).multiply(0.9F),
        Color(119, 26, 176).multiply(1.1F),
        Color(119, 26, 176).multiply(0.9F),
        Color(11, 51, 110).multiply(1.1F),
        Color(11, 51, 110),
        Color(11, 51, 110),
        Color(11, 51, 110).multiply(0.8F),
    )
)

val POLYSEXUAL_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(237, 0, 150).multiply(1.1F),
        Color(237, 0, 150),
        Color(237, 0, 150).multiply(0.9F),
        Color(133, 203, 52).multiply(1.1F),
        Color(133, 203, 52),
        Color(133, 203, 52).multiply(0.9F),
        Color(67, 141, 251).multiply(1.1F),
        Color(67, 141, 251),
        Color(67, 141, 251).multiply(0.8F),
    )
)

val NONBINARY_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(254, 195, 9).multiply(1.1F),
        Color(254, 195, 9),
        Color(254, 195, 9).multiply(0.9F),
        Color(235, 235, 235).multiply(1.1F),
        Color(235, 235, 235),
        Color(235, 235, 235).multiply(0.9F),
        Color(154, 18, 169).multiply(1.1F),
        Color(154, 18, 169),
        Color(154, 18, 169).multiply(0.9F),
        Color(38, 38, 38).multiply(1.1F),
        Color(38, 38, 38),
        Color(38, 38, 38).multiply(0.8F),
    )
)

val ASEXUAL_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(38, 38, 38).multiply(1.1F),
        Color(38, 38, 38),
        Color(38, 38, 38).multiply(0.9F),
        Color(102, 102, 102).multiply(1.1F),
        Color(102, 102, 102),
        Color(102, 102, 102).multiply(0.9F),
        Color(235, 235, 235).multiply(1.1F),
        Color(235, 235, 235),
        Color(235, 235, 235).multiply(0.9F),
        Color(154, 18, 169).multiply(1.1F),
        Color(154, 18, 169),
        Color(154, 18, 169).multiply(0.8F),
    )
)

val AGENDER_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(24, 24, 24).multiply(1.1F),
        Color(24, 24, 24),
        Color(24, 24, 24).multiply(0.9F),
        Color(177, 186, 189).multiply(1.1F),
        Color(177, 186, 189),
        Color(177, 186, 189).multiply(0.8F),
        Color(255, 255, 255).multiply(1.1F),
        Color(255, 255, 255),
        Color(255, 255, 255).multiply(0.85F),
        Color(183, 246, 132).multiply(1.1F),
        Color(183, 246, 132),
        Color(183, 246, 132).multiply(0.9F),
        Color(255, 255, 255).multiply(1.1F),
        Color(255, 255, 255),
        Color(255, 255, 255).multiply(0.85F),
        Color(177, 186, 189).multiply(1.1F),
        Color(177, 186, 189),
        Color(177, 186, 189).multiply(0.8F),
        Color(24, 24, 24).multiply(1.1F),
        Color(24, 24, 24),
        Color(24, 24, 24).multiply(0.8F),
    )
)

val DEMIGIRL_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(112, 112, 112).multiply(1.1F),
        Color(112, 112, 112),
        Color(112, 112, 112).multiply(0.9F),
        Color(196, 196, 196).multiply(1.1F),
        Color(196, 196, 196),
        Color(196, 196, 196).multiply(0.9F),
        Color(253, 173, 200).multiply(1.1F),
        Color(253, 173, 200),
        Color(253, 173, 200).multiply(0.9F),
        Color(255, 255, 255).multiply(1.1F),
        Color(255, 255, 255),
        Color(255, 255, 255).multiply(0.9F),
        Color(253, 173, 200).multiply(1.1F),
        Color(253, 173, 200),
        Color(253, 173, 200).multiply(0.9F),
        Color(196, 196, 196).multiply(1.1F),
        Color(196, 196, 196),
        Color(196, 196, 196).multiply(0.8F),
        Color(112, 112, 112).multiply(1.05F),
        Color(112, 112, 112),
        Color(112, 112, 112).multiply(0.7F)
    )
)

val DEMIBOY_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(112, 112, 112).multiply(1.1F),
        Color(112, 112, 112),
        Color(112, 112, 112).multiply(0.9F),
        Color(196, 196, 196).multiply(1.1F),
        Color(196, 196, 196),
        Color(196, 196, 196).multiply(0.9F),
        Color(157, 215, 234).multiply(1.1F),
        Color(157, 215, 234),
        Color(157, 215, 234).multiply(0.9F),
        Color(235, 235, 235).multiply(1.1F),
        Color(235, 235, 235),
        Color(235, 235, 235).multiply(0.9F),
        Color(157, 215, 234).multiply(1.1F),
        Color(157, 215, 234),
        Color(157, 215, 234).multiply(0.9F),
        Color(196, 196, 196).multiply(1.1F),
        Color(196, 196, 196),
        Color(196, 196, 196).multiply(0.9F),
        Color(112, 112, 112).multiply(1.1F),
        Color(112, 112, 112),
        Color(112, 112, 112).multiply(0.8F)
    )
)

val DEMIFLUX_FLAG_STYLE = SimpleGradientBarStyle(
    listOf(
        Color(112, 112, 112).multiply(1.1F),
        Color(112, 112, 112),
        Color(112, 112, 112).multiply(0.9F),
        Color(218, 218, 218).multiply(1.1F),
        Color(218, 218, 218),
        Color(218, 218, 218).multiply(0.9F),
        Color(255, 181, 183).multiply(1.1F),
        Color(255, 181, 183),
        Color(255, 181, 183).multiply(0.9F),
        Color(243, 246, 145).multiply(1.1F),
        Color(243, 246, 145),
        Color(243, 246, 145).multiply(0.9F),
        Color(153, 217, 234).multiply(1.1F),
        Color(153, 217, 234),
        Color(153, 217, 234).multiply(0.9F),
        Color(218, 218, 218).multiply(1.1F),
        Color(218, 218, 218),
        Color(218, 218, 218).multiply(0.9F),
        Color(112, 112, 112).multiply(1.1F),
        Color(112, 112, 112),
        Color(112, 112, 112).multiply(0.8F),
    )
)