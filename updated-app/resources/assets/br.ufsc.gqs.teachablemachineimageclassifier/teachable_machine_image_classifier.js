// the link to your model provided by Teachable Machine export panel

let model, webcam, webcamPredict, labelContainer, maxPredictions, urlModel;

$(document).ready(function () {
    startWebcam();
    TeachableMachineImageClassifier.ready();
});

// More API functions here:
// https://github.com/googlecreativelab/teachablemachine-community/tree/master/libraries/image

async function setUrlModel(urlGTMModel) {
    urlModel = urlGTMModel;
}

async function startWebcam() {
    // Convenience function to setup a webcam
    const flip = false; // whether to flip the webcam
    webcam = new tmImage.Webcam(500, 500, flip); // width, height, flip
    await webcam.setup(); // request access to the webcam
    await webcam.play();
    loop();
    
    // append elements to the DOM
    $('#webcam-container').parent().children().children().remove();
    document.getElementById("webcam-container").appendChild(webcam.canvas);
}

async function stopWebcam() {
    await webcam.stop();
}

function loop() {
    webcam.update();
    webcamPredict = webcam;
    window.requestAnimationFrame(loop);
}

// Load the image model and setup the webcam
async function classifyVideoData() {

    if(urlModel == null) {
        alert("URL has not been set!");
    }

    const modelURL = urlModel + "model.json";
    const metadataURL = urlModel + "metadata.json";

    // load the model and metadata
    // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
    // or files from your local hard drive
    // Note: the pose library adds "tmImage" object to your window (window.tmImage)
    model = await tmImage.load(modelURL, metadataURL);
    maxPredictions = model.getTotalClasses();

    // predict can take in an image, video or canvas html element
    let result = [];

    const prediction = await model.predict(webcamPredict.canvas);
    for (let i = 0; i < maxPredictions; i++) {
        result.push([prediction[i].className, prediction[i].probability.toFixed(2) * 100]);
    }

    TeachableMachineImageClassifier.reportResult(JSON.stringify(result));
}