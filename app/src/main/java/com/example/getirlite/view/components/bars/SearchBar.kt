package com.example.getirlite.view.components.bars

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.getirlite.R
import com.example.getirlite.view.Screen
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.ui.theme.interpolate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Searchable> SearchBar(
    model: SearchViewModel<T>,
) {
    val focus = remember { FocusRequester() }
    val query by model.searchQuery.collectAsState()
    val isSearching by model.isSearching.collectAsState()
    val keyboard = LocalSoftwareKeyboardController.current

    val trailingIconOpacity by animateFloatAsState(
        targetValue = if (isSearching || query.isNotEmpty()) 1f else 0f,
        label = ""
    )

    BasicTextField(
        value = query,
        onValueChange = model::search,
        textStyle = TextStyle(color = Color.Black),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = query,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = ""
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            model.search("")
                            keyboard?.hide()
                            model.isSearching.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "",
                            modifier = Modifier
                                .alpha(trailingIconOpacity)
                        )
                    }
                },
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                innerTextField = {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        innerTextField()
                    }
                },
                interactionSource = remember { MutableInteractionSource() },
                placeholder = {
                    SimpleText(
                        text = stringResource(id = R.string.search_product),
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(top = 0.dp, bottom = 0.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.White.interpolate(Color.Gray, fraction = 0.3f),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color.Blue.interpolate(Color.Black, fraction = 0.5f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = Color.Blue,
                    unfocusedLeadingIconColor = Color.Blue,
                    focusedTrailingIconColor = Color.Gray.interpolate(Color.White, 0.3f),
                    unfocusedTrailingIconColor = Color.Gray.interpolate(Color.White, 0.3f),
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White,
                )
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                focus.freeFocus()
                keyboard?.hide()
                model.isSearching.value = false
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(Screen.Dimensions.searchBar)
            .focusRequester(focus)
            .onFocusChanged {
                model.isSearching.value = it.hasFocus
            }
    )
}

class SearchViewModel<T: Searchable>(var items: List<T>, val limit: Int, private val searchBehavior: SearchBehaviour = SearchBehaviour.full): ViewModel() {
    var searchQuery = MutableStateFlow("")
    val isSearching = MutableStateFlow(false)
    var filteredItems = MutableStateFlow(if (searchBehavior == SearchBehaviour.empty) emptyList() else items)

    fun set(temp: List<T>) {
        if (items == temp) return
        viewModelScope.launch {
            items = temp
            filteredItems.value = if (searchBehavior == SearchBehaviour.empty) emptyList() else temp
        }
    }

    fun search(query: String) {
        if (query == searchQuery.value) return
        searchQuery.value = query
        viewModelScope.launch {
            filteredItems.value = if (query.isEmpty() and (searchBehavior == SearchBehaviour.empty)) emptyList() else items.asSequence().filter { it.search(query) }.take(limit).toList()
        }
    }

    fun reset() = search(query = "")

    @Suppress("UNCHECKED_CAST")
    class Factory<M: Searchable>(
        private val items: List<M>,
        private val limit: Int = Int.MAX_VALUE,
        private val searchBehavior: SearchBehaviour = SearchBehaviour.full
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SearchViewModel(items = items, limit = limit, searchBehavior = searchBehavior) as T
    }
}

interface Searchable {
    fun search(query: String): Boolean
}

enum class SearchBehaviour { empty, full }