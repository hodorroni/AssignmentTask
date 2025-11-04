package com.rony.assignment.core.presentation.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_MEDIUM_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import timber.log.Timber

@Composable
fun currentDeviceConfig(): DeviceConfiguration {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
}

enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {
            return with(windowSizeClass) {
                when {
                    //edge case for some mobile phones android reports as 0. For example samsung s23 ultra.(my phone)
                    minHeightDp == 0 -> MOBILE_LANDSCAPE
                    minWidthDp < WIDTH_DP_MEDIUM_LOWER_BOUND &&
                            minHeightDp >= HEIGHT_DP_MEDIUM_LOWER_BOUND -> MOBILE_PORTRAIT

                    minWidthDp >= WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp < HEIGHT_DP_MEDIUM_LOWER_BOUND ->{
                        Timber.tag("stamstam").d("landscape")
                        Timber.tag("stamstam").d("got minWidthDp: $minWidthDp")
                        Timber.tag("stamstam").d("got minHeightDp: $minHeightDp")
                                MOBILE_LANDSCAPE
                            }

                    minWidthDp in WIDTH_DP_MEDIUM_LOWER_BOUND..WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp >= HEIGHT_DP_EXPANDED_LOWER_BOUND ->  {
                        Timber.tag("stamstam").d("got minWidthDp: $minWidthDp")
                        Timber.tag("stamstam").d("got minHeightDp: $minHeightDp")
                                TABLET_PORTRAIT
                            }

                    minWidthDp >= WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp in HEIGHT_DP_MEDIUM_LOWER_BOUND..HEIGHT_DP_EXPANDED_LOWER_BOUND -> {
                        Timber.tag("stamstam").d("got minWidthDp: $minWidthDp")
                        Timber.tag("stamstam").d("got minHeightDp: $minHeightDp")
                                TABLET_LANDSCAPE
                            }

                    else -> {
                        Timber.tag("stamstam").d("got minWidthDp: $minWidthDp")
                        Timber.tag("stamstam").d("got minHeightDp: $minHeightDp")
                        DESKTOP
                    }
                }
            }
        }
    }
}