import {ChangeEvent} from "react";
import '../styles/global.css';
import '../styles/input.css';

interface Props {
    id: string;
    label: string;
    type: string;
    handleChange: (value: string) => void;
}

function LabeledTextInput({id, label, type, handleChange}: Props) {
    return (
        <div className="input-container">
            <label>{label}</label>
            <input
                id={id}
                className="input-field"
                type={type}
                onChange={(event: ChangeEvent<HTMLInputElement>) => handleChange(event.target.value)}
            />
        </div>
    );
}

export default LabeledTextInput;
