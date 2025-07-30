package com.varun.gamedevpodcasts.ui.utils

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun longBasicDropdownMenu(): Int? {
    var expanded by remember { mutableStateOf(false) }
    // Placeholder list of 100 strings for demonstration
    val menuItemData = List(100) { it+1 }
    var selectedOption: Int? = null

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItemData.forEach { option ->
                selectedOption = option
                DropdownMenuItem(
                    text = { Text("Season $option") },
                    onClick = {  }
                )
            }
        }
    }
    return selectedOption
}