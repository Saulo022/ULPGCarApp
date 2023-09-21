package com.saulo.ulpgcarapp.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultSearchBar(
    modifier: Modifier,
    query: String,
    onQueryChange: (query: String) -> Unit,
    onSearch: (query: String) -> Unit,
    hideResults: Boolean,
    onActiveChange: (Boolean) -> Unit,
    label: String,
    onClick: () -> Unit,
    searchIcon: ImageVector = Icons.Default.Search
) {

    Column {

        SearchBar(
            query = query,
            onQueryChange = { onQueryChange(it) },
            onSearch = { onSearch(it) },
            active = hideResults,
            onActiveChange = { onActiveChange(it) },
            modifier = modifier.padding(0.dp),
            placeholder = { Text(text = label) },
            leadingIcon = {
                IconButton(onClick = { onClick()  }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
            },
            trailingIcon = { Icon(imageVector = searchIcon, contentDescription = "") }
        ) {

        }

    }
}

/*
Intento1
     DefaultSearchBar(
                modifier = Modifier
                    .padding(8.dp),
                query = stateReturn.searchReturn,
                onQueryChange = {
                    viewModel.onSearchReturnInput(it)
                    viewModel.onSearchReturnSelected()
                },
                onSearch = {
                    Toast.makeText(ctx, stateReturn.searchReturn, Toast.LENGTH_LONG).show()
                },
                label = "Buscar Vuelta ",
                onClick = { viewModel.onSearchReturnDelete() },
                hideResults = active2,
                onActiveChange = { active2 = it}
            )
 */