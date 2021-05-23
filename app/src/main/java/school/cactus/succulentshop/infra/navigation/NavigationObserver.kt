package school.cactus.succulentshop.infra.navigation

import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController

class NavigationObserver {
    fun observe(
        navigation: Navigation,
        navController: NavController,
        lifecycleOwner: LifecycleOwner
    ) {
        navigation.navigateTo.observe(lifecycleOwner) { directions ->
            directions?.let {
                navController.navigate(it)
                navigation.onNavigationComplete()
            }
        }
    }
}