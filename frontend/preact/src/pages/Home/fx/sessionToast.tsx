import { Snackbar, Alert } from "@mui/material";
import { useState } from "preact/hooks";

export default function SessionToast() {
    const [open, setOpen] = useState(false);

    // expose globally however you want
    (window as any).showSessionToast = () => {
        setOpen(true);
    };

    return (
        <Snackbar
            open={open}
            anchorOrigin={{ vertical: "top", horizontal: "center" }}
        >
            <Alert severity="warning" variant="filled">
                Your session expired. Reloading...
            </Alert>
        </Snackbar>
    );
}