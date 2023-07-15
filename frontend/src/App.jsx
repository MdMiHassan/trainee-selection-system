
import React from "react";
import { Layout } from "antd";
import AppHeader from "./layouts/AppHeader";
import AppHero from "./layouts/AppHero";
import ApplicantActivity from "./layouts/ApplicantActivity";
import AppFooter from "./layouts/AppFooter";
import AdminPanel from "./pages/AdminPanel";
import AdminNavbar from "./layouts/AdminNavbar";

const { Header, Content, Footer } = Layout;

function App() {
  return (
    // <Layout style={{minHeight:'100vh',justifyContent:'space-between'}}>
    //   <AppHeader />
    //   <AppHero />
    //   <ApplicantActivity />
    //   <AppFooter />
    // </Layout>
    <>
    {/* <AdminNavbar /> */}
    <AdminPanel />
    </>
  );
}

export default App;
