import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Box from "@mui/material/Box";

export const TopBar = ({ title, subtitle, topBarButtons, shopCart }) => (
	<AppBar position="fixed" color="default" elevation={1} sx={{ zIndex: 1000 }}>
		<Toolbar sx={{ minHeight: 48, display: "flex", alignItems: "center" }}>
			<Box sx={{ display: "flex", alignItems: "center", flexGrow: 1 }}>
				<Typography variant="h6" component="div" sx={{ fontWeight: 600 }}>
					{shopCart ? `Shopping Cart: ${shopCart.cartName}` : title}
				</Typography>
				{subtitle && (
					<Typography variant="subtitle1" sx={{ marginLeft: 2, color: "#757575", fontSize: "1rem" }}>
						{subtitle}
					</Typography>
				)}
			</Box>
			{topBarButtons && topBarButtons.map((button, index) => (
				<Button
					key={index}
					onClick={button.onClick}
					variant="contained"
					color="primary"
					sx={{ marginLeft: 1 }}
				>
					{button.label}
				</Button>
			))}
		</Toolbar>
	</AppBar>
);