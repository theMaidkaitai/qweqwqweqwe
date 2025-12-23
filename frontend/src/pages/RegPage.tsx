import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {useContext, useState} from "react";
import {registration} from "../http/userApi.ts";
import {Context} from "../main.tsx";
import {useNavigate} from "react-router-dom";
import {observer} from "mobx-react-lite";

const RegPage = observer(() => {
    const [nick, setNick] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const {user} = useContext(Context);

    const navigate = useNavigate();

    const clickReg = async (e) => {
        e.preventDefault();
        try {
            const data = await registration(nick, email, password);
            
            user.setUser(data);
            user.setIsAuth(true)
            navigate("/notes")
        }
        catch (e) {
            console.log(e);
        }
    }

    return (
            <Form>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control
                        type="email"
                        placeholder="Enter email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <Form.Text className="text-muted">
                        We'll never share your email with anyone else.
                    </Form.Text>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>Nickname</Form.Label>
                    <Form.Control
                        type="Nickname"
                        placeholder="Nickname"
                        value={nick}
                        onChange={(e) => setNick(e.target.value)}
                    />
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <div style={{ textAlign: "center" }}>
                    Уже есть аккаунт? <a href="/auth/login">Войти</a>
                </div>
                <Button variant="primary" type="submit" onClick={clickReg}>
                    Submit
                </Button>
            </Form>

    );
})

export default RegPage;