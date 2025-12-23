import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import {Button} from "react-bootstrap";
import {useContext, useEffect} from "react";
import {Context} from "../main.tsx";
import {observer} from "mobx-react-lite";
import {useNavigate} from "react-router-dom";
import {getAllInfo} from "../http/userApi.ts";

const NavBar = observer(() => {

    const {user} = useContext(Context);
    const navigate = useNavigate();

    function click () {
        navigate("/auth/login");
        user.setIsAuth(false)
        user.setUser({})
        localStorage.removeItem("token")

    }
    function logout() {
        user.setUser({})
        user.setIsAuth(false)
        localStorage.removeItem("token")
        navigate("/auth/login");
    }


    useEffect(() => {
        getAllInfo().then((data) => {
            console.log(data)
        })
    }, []);

    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand href="/notes">Finance-Notes</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    {user._isAuth ?
                        <Nav className="me-auto">
                            <Nav.Link href="#home">Home</Nav.Link>
                            <Nav.Link href="#link">Cources</Nav.Link>
                            <NavDropdown title="Note-Actions" id="basic-nav-dropdown">
                                <NavDropdown.Item href="#action/3.1">Create note</NavDropdown.Item>
                                <NavDropdown.Item href="#action/3.2">
                                    -
                                </NavDropdown.Item>
                                <NavDropdown.Item href="#action/3.3">-</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                        :
                        <Nav>

                        </Nav>
                    }

                </Navbar.Collapse>
            </Container>
            {user._isAuth ?
                <Button variant={"dark"} style={{marginRight: "50px"}} onClick={logout}>
                    Выйти
                </Button>
                :
                <Button variant={"dark"} style={{marginRight: "50px"}} onClick={click}>
                    Войти
                </Button>
            }
        </Navbar>
    );
})

export default NavBar;