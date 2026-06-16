package vn.anhbt.nutripath.presentation.main.dailylog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DailyLogScreen(modifier: Modifier = Modifier) {
    Text(
        "Daily log",
        modifier = modifier.fillMaxSize().padding(24.dp).wrapContentSize()
    )
}
