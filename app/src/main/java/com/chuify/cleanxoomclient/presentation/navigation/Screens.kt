package com.chuify.cleanxoomclient.presentation.navigation

sealed class Screens(val route: String) {


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

    object QR : Screens("qr") {

        const val arg = "id"

        fun fullRoute(): String {
            return "$route/{$arg}"
        }

        fun routeWithArgs(input: String): String {
            return "$route/$input"
        }
    }


    object Main : Screens("main") {
        fun fullRoute(): String {
            return route
        }
    }

    object Track : Screens("track") {

        const val trackArg = "order"

        fun fullRoute(): String {
            return "$route/{$trackArg}"
        }

        fun routeWithArgs(input: String): String {
            return "$route/$input"
        }

    }

    object EditProfile : Screens("edit_profile") {
        fun fullRoute(): String {
            return route
        }
    }


    object Locations : Screens("locations") {
        fun fullRoute(): String {
            return route
        }
    }

    object Login : Screens("login") {
        fun fullRoute(): String {
            return route
        }
    }

    object Success : Screens("sucess") {
        fun fullRoute(): String {
            return route
        }
    }
}
