package com.example.yarab.presentation.ui.listApi.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.example.yarab.R
import com.example.yarab.domian.model.Recipe
import com.example.yarab.presentation.components.RecipeCard

@ExperimentalMaterialApi
@Composable
fun DataScreen(
    data: List<Recipe>,
    findNavController: NavController
) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(data) { index, item ->
            RecipeCard(
                recipe = item,
                index = index.toString()
            ) {
                val bundle = bundleOf(
                    Pair("Id", item.id),
                )
                findNavController.navigate(
                    R.id.action_listFragment_to_listPagingFragment2,
                    bundle
                )

            }
        }
    }

}



