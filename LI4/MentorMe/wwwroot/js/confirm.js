var button, done, options;
//button = document.ge getElementById("button");

Array.from(document.getElementsByClassName("teste")).forEach(function(button) {
    done = false;
    options = {
        bg: "#C3E6CB",
        bgSize: "215% 100%",
        bgPositionIn: "right bottom",
        bgPositionOut: "left bottom",
        trans: {
            init: true,
            in: 0.5,
            out: 0.5
        },
        gradient: {
            deg: "135deg",
            from_color: "#C3E6CB 50%",
            to_color: "#fa7268 50%"
        },
        question: "🤔 Are you sure?",
        success: {
            message: "👍 Success!",
            color: "#C3E6CB"
        },
        timeout: 3000
    };

    // Init SweetConfirm.js

    new SweetConfirm(button, () => {
        done = true;
    }, options);

    function myFunc() {
        return done;
    }

}
)
// Callback function

