
import logo from "./../assets/ollcalamaison.svg"



export function Header(){
    return (
        <header className="h-10 w-60">
            <a href="/">
                <img src={logo}/>
            </a>
        </header>
    )
}