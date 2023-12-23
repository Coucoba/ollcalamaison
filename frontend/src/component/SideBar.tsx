import { Sidebar } from 'flowbite-react';

import { IoAccessibility } from "react-icons/io5"
import { FaTruck } from "react-icons/fa";
import { PiPackageLight } from "react-icons/pi"

export function SideBar() {
    return (
    <Sidebar className='w-32 h-full' aria-label="Default sidebar example" >
      <Sidebar.Items>
        <Sidebar.ItemGroup>
          <Sidebar.Item href="/deliverypersons" icon={IoAccessibility}>
            Livreur
          </Sidebar.Item>
          <Sidebar.Item href="#" icon={FaTruck}>
            Tournée
          </Sidebar.Item>
          <Sidebar.Item href="#" icon={PiPackageLight}>
            Livraison
          </Sidebar.Item>
        </Sidebar.ItemGroup>
      </Sidebar.Items>
    </Sidebar>
    );
}