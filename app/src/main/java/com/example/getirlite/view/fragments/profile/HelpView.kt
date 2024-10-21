package com.example.getirlite.view.fragments.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.model.extension.double
import com.example.getirlite.model.extension.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpView(sheetState: SheetState, onDismissRequest: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = FAQSection.entries,
                key = { item -> item.name }
            ) { section ->
                FAQSectionItemView(section)
            }
        }
    }
}

@Composable
fun FAQSectionItemView(section: FAQSection) {
    Column {
        SimpleText(
            text = section.title,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(Dp.padding)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
        ) {
            items(
                items = section.items,
                key = { item -> item.name }
            ) { item ->

                FAQItemView(item = item)
            }
        }

    }
}

@Composable
fun FAQItemView(item: FAQItem) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(vertical = Dp.padding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        isExpanded = !isExpanded
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
        ) {
            SimpleText(
                text = item.question,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = Dp.padding)
                    .weight(1f)
            )

            Image(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Blue),
                modifier = Modifier
                    .padding(end = Dp.padding)
                    .size(24.dp)
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically { it },
            exit = shrinkVertically { it }
        ) {
            SimpleText(
                text = item.answer,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(horizontal = Dp.padding.double)
            )
        }
    }
}