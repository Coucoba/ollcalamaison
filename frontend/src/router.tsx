import { createBrowserRouter } from "react-router-dom";
import { Layout } from "./component/Layout";

import route from "./route"
import { HomePage } from "./pages/HomePage";
import { DeliveryPersonPage } from "./pages/DeliveryPersonPage";
import { DeliveryPersonDetailPage } from "./pages/DeliveryPersonDetailPage";
import { ToursPage } from "./pages/ToursPage";
import { DeliveryPage } from "./pages/DeliveryPage";

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
            },
            {
                path: route.DELIVERY_PERSON_DETAIL,
                element: <DeliveryPersonDetailPage/>
            },
            {
                path: route.TOURS,
                element: <ToursPage/>
            },
            {
                path: route.DELIVERIES,
                element: <DeliveryPage/>
            }
        ]
    }
])

export default router;