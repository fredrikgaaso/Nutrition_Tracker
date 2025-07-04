import {Box, Button, Container} from "@mui/material";

export const ButtonGrid = ({ buttons }) => {
    return(
       <>
           <Box
           sx={{
               textAlign: 'center',
               position: 'relative',
           }
           }>
               {buttons.map((button, index) => (
                   <Button
                       key={index}
                       variant="contained"
                       onClick={
                           button.onClick
                               ? button.onClick
                               : () => buttons.action()
                       }

                   >
                       {button.label}
                   </Button>
               ))}


           </Box>
       </>
    )
}