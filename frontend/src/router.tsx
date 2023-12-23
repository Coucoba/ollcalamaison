import { createBrowserRouter } from "react-router-dom";
import { Layout } from "./component/Layout";

import route from "./route"
import { HomePage } from "./pages/HomePage";
import { DeliveryPersonPage } from "./pages/DeliveryPersonPage";

const router = createBrowserRouter([
    {
        element: <Layout/>,
        children: [
            {
                path: route.HOME,
                element: <HomePage/>
            },

            {
                path: route.DELIVERY_PERSON,
                element: <DeliveryPersonPage/>
            }
        ]
    }
])

export default router;