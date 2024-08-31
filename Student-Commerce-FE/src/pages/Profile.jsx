import React, { useEffect, useState } from 'react';
import { Footer, Navbar } from '../components';
import axios from 'axios';
import { backEndUrl } from '../config';

const Profile = () => {
    const [userDefaultAddress, setUserDefaultAddress] = useState(null);
    const [editMode, setEditMode] = useState(false);
    const [formData, setFormData] = useState({
        userId: '',
        addressId: '',
        name: '',
        username: '',
        emailAddress: '',
        phoneNumber: '',
        address1: '',
        address2: '',
        state: '',
        country: '',
        zip: ''
    });
    
    const mapAddress = (userData) => {
        setUserDefaultAddress(userData);
        setFormData({
            userId: userData.user.id,
            addressId: userData.id,
            name: userData.user.name,
            username: userData.user.username,
            emailAddress: userData.user.emailAddress,
            phoneNumber: userData.user.phoneNumber,
            address1: userData.address1,
            address2: userData.address2,
            state: userData.state,
            country: userData.country,
            zip: userData.zip
        });
    }

    const fetchProfile = async () => {
        const authToken = localStorage.getItem("authToken");

        try {
            const response = await axios.get(
                backEndUrl + `/api/address/getUserAddress`,
                {
                    headers: {
                        'Authorization': `Bearer ${authToken}` // Include authToken in headers
                    }
                }
            );

            const userData = response.data[0];
            mapAddress(userData);

        } catch (error) {
            // Handle the error if needed
        }
    };

    useEffect(() => {
        fetchProfile();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({ ...prevState, [name]: value }));
    };

    const handleSave = async () => {
        const authToken = localStorage.getItem("authToken");

        try {
            const response = await axios.post(
                backEndUrl + `/api/user/updateUserInfo`,
                formData,
                {
                    headers: {
                        'Authorization': `Bearer ${authToken}` // Include authToken in headers
                    }
                }
            );

            localStorage.clear();

            const { accessToken, user, address } = response.data;

            localStorage.setItem("authToken", accessToken);
            localStorage.setItem("userId", user.id);
            localStorage.setItem("username", user.username);
            localStorage.setItem("name", user.name);
            localStorage.setItem("phoneNumber", user.phoneNumber);
            localStorage.setItem("emailAddress", user.emailAddress);

            mapAddress(address);

            setEditMode(false);
        } catch (error) {
            console.log("failed");
            // Handle the error if needed
        }
    };

    if (!userDefaultAddress) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <Navbar />
            <div className="container my-3 py-3 d-flex justify-content-center">
                <div className="row d-flex justify-content-center">
                    <div className="col-md-8">
                        <div className="card">
                            <div className="card-header pb-0">
                                <div className="d-flex align-items-center">
                                    <p className="mb-0">User Profile</p>
                                </div>
                            </div>
                            <div className="card-body">
                                <p className="text-uppercase text-sm">User Information</p>
                                <div className="row">
                                    <div className="col-md-6">
                                        <div className="form-group">
                                            <label htmlFor="name" className="form-control-label">Name</label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                id="name"
                                                name="name"
                                                value={formData.name}
                                                onChange={handleChange}
                                                readOnly={!editMode}
                                            />
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="form-group">
                                            <label htmlFor="username" className="form-control-label">Username</label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                id="username"
                                                name="username"
                                                value={formData.username}
                                                onChange={handleChange}
                                                readOnly={!editMode}
                                            />
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="form-group">
                                            <label htmlFor="email" className="form-control-label">Email address</label>
                                            <input
                                                className="form-control"
                                                type="email"
                                                id="email"
                                                name="emailAddress"
                                                value={formData.emailAddress}
                                                onChange={handleChange}
                                                readOnly={!editMode}
                                            />
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="form-group">
                                            <label htmlFor="phoneNumber" className="form-control-label">Phone Number</label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                id="phoneNumber"
                                                name="phoneNumber"
                                                value={formData.phoneNumber}
                                                onChange={handleChange}
                                                readOnly={!editMode}
                                            />
                                        </div>
                                    </div>
                                </div>
                                <hr className="horizontal dark" />
                                <p className="text-uppercase text-sm">Default Address Information</p>
                                <div className="row">
                                    <div className="col-md-6">
                                        <div className="form-group">
                                            <label htmlFor="address1" className="form-control-label">Address 1</label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                id="address1"
                                                name="address1"
                                                value={formData.address1}
                                                onChange={handleChange}
                                                readOnly={!editMode}
                                            />
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="form-group">
                                            <label htmlFor="address2" className="form-control-label">Address 2</label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                id="address2"
                                                name="address2"
                                                value={formData.address2}
                                                onChange={handleChange}
                                                readOnly={!editMode}
                                            />
                                        </div>
                                    </div>
                                    <div className="col-md-4">
                                        <div className="form-group">
                                            <label htmlFor="state" className="form-control-label">State</label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                id="state"
                                                name="state"
                                                value={formData.state}
                                                onChange={handleChange}
                                                readOnly={!editMode}
                                            />
                                        </div>
                                    </div>
                                    <div className="col-md-4">
                                        <div className="form-group">
                                            <label htmlFor="country" className="form-control-label">Country</label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                id="country"
                                                name="country"
                                                value={formData.country}
                                                onChange={handleChange}
                                                readOnly={!editMode}
                                            />
                                        </div>
                                    </div>
                                    <div className="col-md-4">
                                        <div className="form-group">
                                            <label htmlFor="zip" className="form-control-label">Postal code</label>
                                            <input
                                                className="form-control"
                                                type="text"
                                                id="zip"
                                                name="zip"
                                                value={formData.zip}
                                                onChange={handleChange}
                                                readOnly={!editMode}
                                            />
                                        </div>
                                    </div>
                                    <div className='row edit-save-profile'>
                                        {!editMode && (
                                            <button className="btn btn-primary ml-auto" onClick={() => setEditMode(true)}>Edit Profile</button>
                                        )}
                                        {editMode && (
                                            <button className="btn btn-success ml-auto" onClick={handleSave}>Save</button>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <Footer />
        </div>
    );
}

export default Profile;
