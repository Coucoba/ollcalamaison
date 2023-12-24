import { RouterProvider } from 'react-router-dom'
import './App.css'
import router from "./router"
import { useState } from 'react'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'

function App() {
  const [queryClient] = useState(() => new QueryClient());
  return (
    <>
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router}/>
      </QueryClientProvider>
    </>
  )
}

export default App
