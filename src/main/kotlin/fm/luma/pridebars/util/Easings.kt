/*
 * Copyright (c) 2025 Pixelground Labs - All Rights Reserved.
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package fm.luma.pridebars.util

import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

enum class EasingMode(val displayName: String) {
    LINEAR("Linear") {
        override fun ease(time: Double): Double {
            return time
        }
    },
    SINE("Sine") {
        override fun ease(time: Double): Double {
            return sin((time * PI) / 2.0)
        }
    },
    CUBIC("Cubic") {
        override fun ease(time: Double): Double {
            return 1.0 - (1.0 - time).pow(3.0)
        }
    },
    CIRC("Circle") {
        override fun ease(time: Double): Double {
            return sqrt(1 - ((time - 1).pow(2)))
        }
    },
    QUINT("Quint") {
        override fun ease(time: Double): Double {
            return 1.0 - (1 - time).pow(5.0)
        }
    },
    QUAD("Quad") {
        override fun ease(time: Double): Double {
            return 1.0 - (1.0 - time) * (1.0 - time)
        }
    },
    QUART("Quart") {
        override fun ease(time: Double): Double {
            return 1.0 - ((1 - time).pow(4))
        }
    },
    EXPO("Expo") {
        override fun ease(time: Double): Double {
            return if (time == 1.0) 1.0 else 1.0 - (2.0.pow(-10 * time))
        }
    };

    override fun toString(): String {
        return displayName
    }

    abstract fun ease(time: Double): Double
}

fun getEasing(name: String): EasingMode {
    for (entry in EasingMode.entries) {
        if (entry.name.equals(name, true)) {
            return entry
        }
    }
    return EasingMode.LINEAR
}
