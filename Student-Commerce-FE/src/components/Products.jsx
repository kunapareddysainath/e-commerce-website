import React, { useState, useEffect } from "react";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const domainUrlForProduct = "http://localhost:3000/product/"
const backEndUrl = "http://localhost:8080";


const   Products = () => {
  const [data, setData] = useState([]);
  const [filter, setFilter] = useState(data);
  const [loading, setLoading] = useState(false);
  let componentMounted = true;

  const navigate = useNavigate();

  const addProduct = async (product) => {

    const authToken = localStorage.getItem("authToken");
    // Add quality to the product object
    product.quantity = 1;
    product.productId = product.id;

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

      if (response.data) {
        toast.success(product.title + "to added in the cart");
      }

    } catch (error) {
      // Handle the error if needed
      toast.error("Failed to add the product in the cart");
    }
  };


  useEffect(() => {
    const getProducts = async () => {
      setLoading(true);
      try {
        const response = await fetch("https://fakestoreapi.com/products/");
        if (componentMounted) {
          setData(await response.clone().json());
          setFilter(await response.json());
          setLoading(false);
        }

        return () => {
          componentMounted = false;
        };
      } catch (error) {
        console.log(error);
        navigate("/pagenotfound");
      }
    };

    getProducts();
  }, []);

  const Loading = () => {
    return (
      <>
        <div className="col-12 py-5 text-center">
          <Skeleton height={40} width={560} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
      </>
    );
  };

  const filterProduct = (cat) => {
    const updatedList = data.filter((item) => item.category === cat);
    setFilter(updatedList);
  }
  const ShowProducts = () => {
    return (
      <>
        <div className="buttons text-center py-5">
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => setFilter(data)}>All</button>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => filterProduct("men's clothing")}>Men's Clothing</button>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => filterProduct("women's clothing")}>Women's Clothing</button>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => filterProduct("jewelery")}>Jewelery</button>
          <button className="btn btn-outline-dark btn-sm m-2" onClick={() => filterProduct("electronics")}>Electronics</button>
        </div>

        {filter.map((product) => {
          return (
            <div id={product.id} key={product.id} className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
              <div className="card text-center h-100" key={product.id}>
                <img
                  className="card-img-top p-3"
                  src={product.image}
                  alt="Card"
                  height={300}
                />
                <div className="card-body">
                  <h5 className="card-title">
                    {product.title.substring(0, 12)}...
                  </h5>
                  <p className="card-text">
                    {product.description.substring(0, 90)}...
                  </p>
                </div>
                <ul className="list-group list-group-flush">
                  <li className="list-group-item lead">₹ {(product.price) * 10}</li>
                </ul>
                <div className="card-body">
                  <button className="btn btn-dark m-1" onClick={() => addProduct(product)}>
                    Add to Cart
                  </button>
                  <button className="btn btn-dark m-1" onClick={() => handleShare(product)}>
                    <i className="fas fa-share pr-2" />Whatsapps
                  </button>
                </div>
              </div>
            </div>

          );
        })}
      </>
    );
  };

  const handleShare = (product) => {
    // Construct the WhatsApp share link
    const productUrl = domainUrlForProduct + product.id;
    const whatsappMessage = `Check out this amazing product: ${product.title}\n${productUrl}`;
    const whatsappUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(whatsappMessage)}`;
    // Redirect the user to WhatsApp
    window.open(whatsappUrl, '_blank');
  };


  return (
    <>
      <div className="container my-3 py-3">
        <div className="row">
          <div className="col-12">
            <h2 className="display-5 text-center">Latest Products</h2>
            <hr />
          </div>
        </div>
        <div className="row justify-content-center">
          {loading ? <Loading /> : <ShowProducts />}
        </div>
      </div>
      <ToastContainer />
    </>
  );
};

export default Products;
