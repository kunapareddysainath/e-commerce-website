import React, { useState, useRef, useEffect } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';

const Navbar = () => {
    const navigation = useNavigate();

    const handleLogout = () => {
        localStorage.clear();
        navigation("/");
    };

    useEffect(() => {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) {
            navigation('/login');
        }
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [navigation]);

    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const dropdownRef = useRef(null);

    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };

    const handleClickOutside = (event) => {
        if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
            setIsDropdownOpen(false);
        }
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light py-3 sticky-top">
            <div className="container">
                <NavLink className="navbar-brand fw-bold fs-4 px-2" to="/"><i>Student Ecommerce</i></NavLink>

                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav m-auto my-2 text-center">
                        <li className="nav-item">
                            <NavLink className="nav-link font-weight-bold font-italic" to="/">Home</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link font-weight-bold font-italic" to="/product">Products</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link font-weight-bold font-italic" to="/about">About</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link font-weight-bold font-italic" to="/contact">Contact</NavLink>
                        </li>
                    </ul>
                    <div className="buttons text-center">
                        <NavLink to="/cart" className="btn btn-outline-dark m-2">
                            <i className="fa fa-cart-shopping mr-1"></i> Cart
                        </NavLink>
                    </div>
                    <div
                        style={{ paddingLeft: '20px', paddingRight: '0px', cursor: 'pointer' }}
                        ref={dropdownRef}
                        className="dropdown"
                    >
                        <i
                            className="btn btn-outline-dark m-2"
                            onClick={toggleDropdown}
                            aria-expanded={isDropdownOpen}
                            aria-haspopup="true"
                            id="dropdownMenuButton"
                        > {localStorage.getItem('username')} </i>
                        <div className={`dropdown-menu${isDropdownOpen ? ' show' : ''}`} aria-labelledby="dropdownMenuButton">
                            <div className="dropdown-item m-2">
                                <NavLink className="nav-link" to="/profile">
                                    <i className="fa fa-user mr-1" />
                                    Profile
                                </NavLink>
                            </div>
                            <div className="dropdown-item m-2">
                                <NavLink className="nav-link" to="/orders">
                                    <i className="fa fa-box mr-1" />
                                    Orders
                                </NavLink>
                            </div>
                            <div className="dropdown-item m-2" onClick={handleLogout}>
                                <i className="fa fa-sign-out mr-1" />
                                Log out
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;
