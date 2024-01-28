import { RouterProvider } from 'react-router-dom'
import './App.css'
import router from "./router"
import { useState } from 'react'
import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { LocalizationProvider } from '@mui/x-date-pickers'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'

function App() {
  const [queryClient] = useState(() => new QueryClient());
  return (
    <>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <QueryClientProvider client={queryClient}>
         <RouterProvider router={router}/>
        </QueryClientProvider>
      </LocalizationProvider>
    </>
  )
}

export default App
