package com.leo.unipiaudiostories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.leo.unipiaudiostories.utils.StatsManager

@Composable
fun StatsItemView(
    stats: StatsManager,
) {
    val configuration = LocalConfiguration.current
    val fontScale = configuration.fontScale
    println(stats.getTitleToCount())
    val sortedStatsMap = stats.getTitleToCount().entries
        .sortedByDescending { it.value }
        .associate { it.key to it.value }
    val maxReadCount = sortedStatsMap.entries.firstOrNull()?.value

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.times_heard),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        maxReadCount?.let { maxCount ->
            sortedStatsMap.forEach { storyStat ->
                val width = when(storyStat.value) {
                    0 -> 1f
                    else -> storyStat.value / maxCount.toFloat()
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(width)
                            .padding(end = 8.dp)
                            .height((fontScale * 36).dp)
                            .background(color = getStatLineColor(storyStat.value)),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = storyStat.key,
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Text(
                        modifier = Modifier.weight(fontScale * 0.2f / width),
                        text = when (storyStat.value) {
                            0 -> ""
                            else -> storyStat.value.toString()
                        },
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }

            }
        }
    }
}

@Composable
private fun getStatLineColor(readCount: Int): Color {
    return when (readCount) {
        0 -> Color.Transparent
        else -> MaterialTheme.colorScheme.primary
    }
}