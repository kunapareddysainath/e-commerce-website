import React, { useEffect, useState } from "react";
import { Footer, Navbar } from "../components";
import { Link } from "react-router-dom";
import axios from "axios";
const backEndUrl = "http://localhost:8080";

const Cart = () => {

  const [products, setProducts] = useState([]);

  const EmptyCart = () => {
    return (
      <div className="container">
        <div className="row">
          <div className="col-md-12 py-5 bg-light text-center">
            <h4 className="p-3 display-5">Your Cart is Empty</h4>
            <Link to="/" className="btn  btn-outline-dark mx-4">
              <i className="fa fa-arrow-left"></i>
              Continue Shopping
            </Link>
          </div>
        </div>
      </div>
    );
  };

  const addItem = async (product) => {

    const authToken = localStorage.getItem("authToken");
    // Add quality to the product object
    product.quantity = 1;

    try {
      const response = await axios.post(
        backEndUrl + "/api/cart/addProduct",
        product, // Send the modified product object as the request body
        {
          headers: {
            'Authorization': `Bearer ${authToken}` // Include authToken in headers
          }
        }
      );
      // Handle the response if needed
      fetchProducts();
    } catch (error) {
      // Handle the error if needed
    }
  };

  const removeItem = async (product) => {
    const authToken = localStorage.getItem("authToken");
    // Add quality to the product object
    product.quantity = 1;

    try {
      const response = await axios.post(
        backEndUrl + "/api/cart/deleteProduct",
        product, // Send the modified product object as the request body
        {
          headers: {
            'Authorization': `Bearer ${authToken}` // Include authToken in headers
          }
        }
      );
      // Handle the response if needed
      fetchProducts();
    } catch (error) {
      // Handle the error if needed
    }
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

  useEffect(() => {
    fetchProducts();
  }, []);


  const ShowCart = () => {
    const subtotal = products.reduce((total, item) => total + (item.price * item.quantity), 0);
    let shipping = 40.0;

    return (
      <>
        <section className="h-100 gradient-custom">
          <div className="container py-5">
            <div className="row d-flex justify-content-center my-4">
              <div className="col-md-8">
                <div className="card mb-4">
                  <div className="card-header py-3">
                    <h5 className="mb-0">Item List</h5>
                  </div>
                  <div className="card-body">
                    {products.map((item) => {
                      return (
                        <div key={item.id}>
                          <div className="row d-flex align-items-center">
                            <div className="col-lg-3 col-md-12">
                              <div
                                className="bg-image rounded"
                                data-mdb-ripple-color="light"
                              >
                                <img
                                  src={item.imageUrl}
                                  // className="w-100"
                                  alt={item.title}
                                  width={100}
                                  height={75}
                                />
                              </div>
                            </div>

                            <div className="col-lg-5 col-md-6">
                              <p>
                                <strong>{item.title}</strong>
                              </p>
                            </div>

                            <div className="col-lg-4 col-md-6">
                              <div
                                className="d-flex mb-4"
                                style={{ maxWidth: "300px" }}
                              >
                                <button
                                  className="btn px-3"
                                  onClick={() => {
                                    removeItem(item);
                                  }}
                                >
                                  <i className="fas fa-minus"></i>
                                </button>

                                <p className="mx-5">{item.quantity}</p>

                                <button
                                  className="btn px-3"
                                  onClick={() => {
                                    addItem(item);
                                  }}
                                >
                                  <i className="fas fa-plus"></i>
                                </button>
                              </div>

                              <p className="text-start text-md-center">
                                <strong>
                                  <span className="text-muted">{item.quantity}</span>{" "}
                                  x ₹{item.price} = {item.quantity * item.price}
                                </strong>
                              </p>
                            </div>
                          </div>
                          <hr className="my-4" />
                        </div>
                      );
                    })}
                  </div>
                </div>
              </div>
              <div className="col-md-4">
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
                          <strong>₹{Math.round(subtotal + shipping)}</strong>
                        </span>
                      </li>
                    </ul>

                    <Link
                      to="/checkout"
                      className="btn btn-dark btn-lg btn-block"
                    >
                      Go to checkout
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </>
    );
  };

  return (
    <>
      <Navbar />
      <div className="container my-3 py-3">
        <h1 className="text-center">Cart</h1>
        <hr />
        {products.length > 0 ? <ShowCart /> : <EmptyCart />}
      </div>
      <Footer />
    </>
  );
};

export default Cart;
