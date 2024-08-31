import React, { useEffect, useState } from "react";
import { Footer, Navbar } from "../components";
import customAlert from "sweetalert2";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { stateNames } from "../assests/statesNames";
import { backEndUrl, razorpay_key_id, companyName } from "../config.js";

const Checkout = () => {

  const storedName = localStorage.getItem('name') || '';
  const storedPhoneNumber = localStorage.getItem('phoneNumber') || '';
  const storedEmailAddress = localStorage.getItem('emailAddress') || '';

  const [products, setProducts] = useState([]);
  const [paymentMethod, setPaymentMethod] = useState('online');
  const [address, setAddress] = useState([]);
  const [selectedAddress, setSelectedAddress] = useState(null);
  const [openAddressList, setOpenAddressList] = useState(true);

  const navigate = useNavigate();

  const handleCheckboxChange = (method) => {
    setPaymentMethod(method);
  };

  const fetchProducts = async () => {
    const userId = localStorage.getItem("userId");
    const authToken = localStorage.getItem("authToken");

    try {
      const response = await axios.get(
        backEndUrl + `/api/cart/getCartedProducts?userId=${userId}`
        ,
        {
          headers: {
            'Authorization': `Bearer ${authToken}` // Include authToken in headers
          }
        }
      );
      setProducts(response.data);
      // Handle the response if needed
    } catch (error) {
      // Handle the error if needed
    }
  };

  const fetchAddress = async () => {
    const authToken = localStorage.getItem("authToken");

    try {
      const response = await axios.get(
        backEndUrl + `/api/address/getUserAddress`
        ,
        {
          headers: {
            'Authorization': `Bearer ${authToken}` // Include authToken in headers
          }
        }
      );
      if(response.data !=='Addresses is not found for the user'){
      setAddress(response.data)
      setSelectedAddress(response.data[0]);
      }
    } catch (error) {
      // Handle the error if needed
    }
  };

  useEffect(() => {
    fetchProducts();
    fetchAddress();
  }, []);

  const AddressForm = () => {
    const [address1, setAddress1] = useState('');
    const [address2, setAddress2] = useState('');
    const [country, setCountry] = useState('');
    const [state, setState] = useState('');
    const [zip, setZip] = useState('');

    const handleAddressSave = async (e) => {

      e.preventDefault();

      const authToken = localStorage.getItem("authToken");

      const reqestBody = {
        address1,
        address2,
        country,
        state,
        zip
      };

      try {
        await axios.post(
          backEndUrl + `/api/address/saveAddress`, reqestBody,
          {
            headers: {
              'Authorization': `Bearer ${authToken}`
            }
          }
        );
        fetchAddress();
        setOpenAddressList(false);
        // Handle the response if needed
      } catch (error) {
        // Handle the error if needed
        console.error(error);
      }
    };

    return (
      <>
        <div className="d-flex justify-content-center align-items-center">
          <div className="col-6 my-1">
            <label htmlFor="address" className="form-label">
              Address
            </label>
            <input
              type="text"
              className="form-control"
              id="address"
              placeholder="1234 Main St"
              required
              value={address1}
              onChange={(e) => setAddress1(e.target.value)}
            />
            <div className="invalid-feedback">
              Please enter your shipping address.
            </div>
          </div>

          <div className="col-6">
            <label htmlFor="address2" className="form-label">
              Address 2 <span className="text-muted">(Optional)</span>
            </label>
            <input
              type="text"
              className="form-control"
              id="address2"
              placeholder="Apartment or suite"
              value={address2}
              onChange={(e) => setAddress2(e.target.value)}
            />
          </div>
        </div>

        <div className="d-flex">
          <div className="col-md-5 my-1">
            <label htmlFor="country" className="form-label">
              Country
            </label>
            <br />
            <select
              className="form-select"
              id="country"
              required
              value={country}
              onChange={(e) => setCountry(e.target.value)}
            >
              <option value="">Choose...</option>
              <option>India</option>
            </select>
            <div className="invalid-feedback">
              Please select a valid country.
            </div>
          </div>

          <div className="col-md-4 my-1">
            <label htmlFor="state" className="form-label">
              State
            </label>
            <br />
            <select
              className="form-select"
              id="state"
              required
              value={state}
              onChange={(e) => setState(e.target.value)}
            >
              <option value="">Choose...</option>
              {stateNames.map((item, index) => {
                return (<option key={index}>{item}</option>)
              })}
            </select>
            <div className="invalid-feedback">
              Please provide a valid state.
            </div>
          </div>

          <div className="col-md-3 my-1">
            <label htmlFor="zip" className="form-label">
              Zip
            </label>
            <input
              type="text"
              className="form-control"
              id="zip"
              placeholder=""
              required
              value={zip}
              onChange={(e) => setZip(e.target.value)}
            />
            <div className="invalid-feedback">
              Zip code required.
            </div>
          </div>
        </div>
        <button
          className="w-100 btn btn-primary"
          type="submit"
          onClick={handleAddressSave}
        >
          Save Address
        </button>
      </>
    );
  };

  const PaymentForm = () => {

    return (
      <>
        <h4 className="mb-3">Payment</h4>
        <div className="row gy-3">
          <div className="col-md-6">
            <div className="form-check">
              <input
                type="checkbox"
                id="online-payment"
                name="payment-method"
                className="form-check-input"
                checked={paymentMethod === 'online'}
                onChange={() => handleCheckboxChange('online')}
              />
              <label htmlFor="online-payment" className="form-check-label pr-2">
                Online payment
              </label>
            </div>
          </div>
          <div className="col-md-6">
            <div className="form-check">
              <input
                type="checkbox"
                id="cod-payment"
                name="payment-method"
                className="form-check-input"
                checked={paymentMethod === 'cod'}
                onChange={() => handleCheckboxChange('cod')}
              />
              <label htmlFor="cod-payment" className="form-check-label pr-2">
                Cash on delivery
              </label>
            </div>
          </div>
        </div>
      </>
    )
  };

  const handleSelectedAddress = (address) => {
    setSelectedAddress(address);
  }

  const AddressList = () => {

    const toggleOpenAddressform = () => {
      setOpenAddressList();
    }

    return (
      <>
        <div className="d-flex align-items-center justify-content-between">
          <h5 className="mb-0">Select Address</h5>
          <button className="btn btn-dark" onClick={toggleOpenAddressform}>Add Address</button>
        </div>

        {address.map((item, index) => (
          <div className="col-12 form-check" key={index}>
            <div className="d-flex align-items-center pl-4">
              <input
                type="radio"
                name="selected-address"
                className="form-check-input"
                checked={selectedAddress === item}
                onChange={() => handleSelectedAddress(item)}
              />
              <p className="pl-2 mb-0">{item.address1}, {item.address2}, {item.state}, {item.country} - {item.zip}</p>
            </div>
          </div>
        ))}
      </>
    );
  };

  const ShowCheckout = () => {

    const [name, setName] = useState(storedName);
    const [phoneNumber, setPhoneNumber] = useState(storedPhoneNumber);
    const [emailAddress, setEmailAddress] = useState(storedEmailAddress);

    const subtotal = products.reduce((total, item) => total + (item.price * item.quantity), 0);
    let shipping = 40.0;

    const handleOnlinePayment = async () => {

      const authToken = localStorage.getItem("authToken");

      const reqestBody = {
        amount: Math.round(subtotal + shipping),
        paymentMethod: "ONLINE",
        productList: products,
        address: selectedAddress
      };

      const orderResponse = await axios.post(backEndUrl + '/api/order/create ', reqestBody,
        {
          headers: {
            'Authorization': `Bearer ${authToken}`
          }
        }
      );

      const { orders, razorpayOrder } = orderResponse.data;

      const options = {

        key: razorpay_key_id, // Enter the Key ID generated from the RAZOR Pay Dashboard
        amount: razorpayOrder.amount,
        currency: "INR",
        name: companyName,
        description: 'Student Commerce Transaction',
        order_id: razorpayOrder.id,

        handler: async function (response) {
          const paymentVerificationResponse = await axios.post(backEndUrl + '/api/payment/verify', {
            orderId: orders.id,
            razorpayOrderId: response.razorpay_order_id,
            paymentId: response.razorpay_payment_id,
            amount: razorpayOrder.amount,
          }, {
            headers: {
              'Authorization': `Bearer ${authToken}`
            }
          });
          if (paymentVerificationResponse.status === 200) {
            navigate("/orders");
          } else {
            alert('Payment verification failed');
          }
        },
        prefill: {
          name: storedName,
          email: storedEmailAddress,
          contact: storedPhoneNumber,
        },
        theme: {
          color: '#F37254',
        },
      };

      const rzp1 = new window.Razorpay(options);
      rzp1.open();
    };

    const handleCODPayment = async () => {
      const authToken = localStorage.getItem("authToken");

      const reqestBody = {
        amount: Math.round(subtotal + shipping),
        paymentMethod: "Cash on Delivery",
        productList: products,
        address: selectedAddress
      };

      try {
        await axios.post(
          backEndUrl + `/api/order/create`, reqestBody,
          {
            headers: {
              'Authorization': `Bearer ${authToken}`
            }
          }
        );

        navigate("/orders");

        // Handle the response if needed
      } catch (error) {
        // Handle the error if needed
        console.error(error);
      }
    }

    const handlePayment = () => {

      if (paymentMethod === 'online') {
        handleOnlinePayment();
      } else {
        handleCODPayment();
      }
    };

    const handleOrderAccept = (e) => {
      e.preventDefault();
      customAlert.fire({
        title: "Order Acception",
        text: "Are you sure you want to accept this order?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes",
        cancelButtonText: "No"
      }).then((result) => {
        if (result.isConfirmed) {
          // Handle order acceptance
          handlePayment();
        }
      })
    };

    return (
      <>
        <div className="container py-5">
          <div className="row my-4">
            <div className="col-md-5 col-lg-4 order-md-last">
              <div className="card mb-4">
                <div className="card-header py-3 bg-light">
                  <h5 className="mb-0">Order Summary</h5>
                </div>
                <div className="card-body">
                  <ul className="list-group list-group-flush">
                    <li className="list-group-item d-flex justify-content-between align-items-center border-0 px-0 pb-0">
                      Products ({products.length})<span>₹{Math.round(subtotal)}</span>
                    </li>
                    <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                      Shipping
                      <span>₹{shipping}</span>
                    </li>
                    <li className="list-group-item d-flex justify-content-between align-items-center border-0 px-0 mb-3">
                      <div>
                        <strong>Total amount</strong>
                      </div>
                      <span>
                        <strong>₹ {Math.round(subtotal + shipping)}</strong>
                      </span>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div className="col-md-7 col-lg-8">
              <div className="card mb-4">
                <div className="card-header py-3">
                  <h4 className="mb-0">Billing address</h4>
                </div>
                <div className="card-body">
                  <form className="needs-validation" noValidate onSubmit={handleOrderAccept}>
                    <div className="row g-3">
                      <div className="d-flex justify-content-center align-items-center">
                        <div className="col-4 my-1">
                          <label htmlFor="firstName" className="form-label">
                            Your name
                          </label>
                          <input
                            type="text"
                            className="form-control"
                            id="firstName"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                          />
                          <div className="invalid-feedback">
                            Valid first name is required.
                          </div>
                        </div>

                        <div className="col-4 my-1">
                          <label htmlFor="email" className="form-label">
                            Email
                          </label>
                          <input
                            type="email"
                            className="form-control"
                            id="email"
                            value={emailAddress}
                            onChange={(e) => setEmailAddress(e.target.value)}
                            required
                          />
                          <div className="invalid-feedback">
                            Please enter a valid email address for shipping
                            updates.
                          </div>

                        </div>

                        <div className="col-4 my-1">
                          <label htmlFor="email" className="form-label">
                            Phone Number
                          </label>
                          <input
                            type="email"
                            className="form-control"
                            id="email"
                            value={phoneNumber}
                            onChange={(e) => setPhoneNumber(e.target.value)}
                            required
                          />
                          <div className="invalid-feedback">
                            Please enter a valid email address for shipping
                            updates.
                          </div>

                        </div>

                      </div>
                      {(address.length > 0 && openAddressList) ? <AddressList /> : <AddressForm />}
                    </div>
                    <hr className="my-4" />
                    <PaymentForm />
                    <hr className="my-4" />

                    <button
                      className="w-100 btn btn-primary "
                      type="submit"
                    >
                      Order Now
                    </button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </>
    );
  };

  const EmptyCart = () => {
    return (
      <div className="container">
        <div className="row">
          <div className="col-md-12 py-5 bg-light text-center">
            <h4 className="p-3 display-5">No item in Cart</h4>
            <Link to="/" className="btn btn-outline-dark mx-4">
              <i className="fa fa-arrow-left"></i> Continue Shopping
            </Link>
          </div>
        </div>
      </div>
    );
  };

  return (
    <>
      <Navbar />
      <div className="container py-3">
        <h1 className="text-center">Checkout</h1>
        {products.length ? <ShowCheckout /> : <EmptyCart />}
      </div>
      <Footer />
    </>
  );
};

export default Checkout;
