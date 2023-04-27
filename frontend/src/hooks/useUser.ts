import {useState} from "react";

export function useUser(): [any, any] {
    const [name, setName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [role, setRole] = useState<string>("USER");

    return [{name, email, password, role}, {name: setName, email: setEmail, password: setPassword, role: setRole}]
}
