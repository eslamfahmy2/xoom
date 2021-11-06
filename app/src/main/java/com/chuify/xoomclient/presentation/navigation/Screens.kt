package com.chuify.xoomclient.presentation.navigation

sealed class Screens(val route: String) {
    object Vendors : Screens("vendor")

    object VendorDetails : Screens("vendor_details") {

        const val vendorArg = "vendor"

        fun fullRoute(): String {
            return "$route/{$vendorArg}"
        }

        fun routeWithArgs(input: String): String {
            return "$route/$input"
        }

    }

    object AccessoryDetails : Screens("accessory_details") {

        const val accessoryArg = "accessory"

        fun fullRoute(): String {
            return "$route/{$accessoryArg}"
        }

        fun routeWithArgs(input: String): String {
            return "$route/$input"
        }

    }

    object Cart : Screens("cart") {

        fun fullRoute(): String {
            return route
        }
    }

    object Checkout : Screens("checkout") {

        fun fullRoute(): String {
            return route
        }
    }

    object PaymentMethod : Screens("payment_method") {
        fun fullRoute(): String {
            return route
        }
    }

    object PickLocation : Screens("pick_location") {
        fun fullRoute(): String {
            return route
        }
    }


    object Main : Screens("main") {
        fun fullRoute(): String {
            return route
        }
    }

    object Track : Screens("track") {
        fun fullRoute(): String {
            return route
        }
    }

    object EditProfile : Screens("edit_profile") {
        fun fullRoute(): String {
            return route
        }
    }
}
