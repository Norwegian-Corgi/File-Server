import {useState} from "react";
import downArrowIcon from "../assets/downArrowIcon.svg";
import upArrowIcon from "../assets/upArrowIcon.svg";
import "../styles/dropdown.css";

interface DropdownItem {
    label: string;
    value: string;
}

interface DropdownProps {
    defaultSelection: DropdownItem | undefined;
    items: DropdownItem[];
    onSelect: (value: string) => void;
}

function Dropdown({defaultSelection, items, onSelect}: DropdownProps) {
    const [isOpen, setIsOpen] = useState<boolean>(false);

    const handleItemClick = (value: string) => {
        onSelect(value);
        setIsOpen(false);
    };

    return (
        <div className="dropdown">
            <button
                className="dropdown-toggle"
                onClick={() => setIsOpen(!isOpen)}
            >
                {defaultSelection?.label ?? "Select an option"}
                <img className="" alt="icon" src={isOpen ? upArrowIcon : downArrowIcon}/>
            </button>
            {isOpen && (
                <ul className="dropdown-menu">
                    {items.map((item) => (
                        <li
                            key={item.value}
                            className="dropdown-item"
                            onClick={() => handleItemClick(item.value)}
                        >
                            {item.label}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default Dropdown;
