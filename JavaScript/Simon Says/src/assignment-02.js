
const DEFAULT_SPEED = 500;
const START_DELAY = 3000;
const STAGES = [6, 10, 14];
const COLOURS = [['#4f9853', '#20FF04'], ['#ae3537', 'red'], ['#455b9b', 'dodgerblue'], ['#908c1c', 'yellow']];

let highScore = 0, currentScore = 0;    // Score counters
let standbyPhase = true;               // Block input or not
let input, sequence = [], count;
let speed;

$(".start").click(function startGame() {
    standbyPhase = true;
    $("#led").css('background-color', '#00D000');
    currentScore = 0;
    sequence = [];
    count = 0;
    speed = DEFAULT_SPEED;
    $(".current_score").html(currentScore);
    setTimeout(function(){ gameLoop() }, START_DELAY);
});

function gameLoop() {
    standbyPhase = true;
    count = 0;
    sequence.push(Math.floor(Math.random() * 4));   // Add a new color to the sequence [0-3]
    switch (sequence.length - 1) {                      // Define the steps at which the game speeds up.
        case STAGES[0]: speed = speed / 2;
            break;
        case STAGES[1]: speed = speed / 1.5;
            break;
        case STAGES[2]: speed = speed / 1.25;
            break;
    }
    replaySequence();
}

/**
 * Call to display the sequence of colours on the Simon device.
 */
function replaySequence() {
    for (let i = 0; i < sequence.length; i++) {
        //pick button to flash, nth-child index begins at 1
        let button = $(`.seg:nth-child(${sequence[i] + 1})`);
        setTimeout(function(){ //turn on button
            button.css("background-color", COLOURS[sequence[i]][1]);
            setTimeout(function(){  //turn off button
                button.css("background-color", COLOURS[sequence[i]][0]);
            }, speed / 1.1);
        }, speed * i);
    }
    standbyPhase = false;
}

function endGame() {
    standbyPhase = true;
    $(".indicator").css("background-color", "#D00000");
    let lossFlashCount = 0;
    let lossFlashInterval = setInterval(function lossFlash(){
        // Turn on every colour
        for(let i = 0; i < 4; i++){
            $(`.seg:nth-child( ${i + 1} )`).css("background-color", COLOURS[i][1]);
        }
        // Turn off every colour
        setTimeout(function() {
            for(let i = 0; i <= 4; i++){
                $(`.seg:nth-child( ${i + 1} )`).css("background-color", COLOURS[i][0]);
            }
        }, 100);
        if(++lossFlashCount === 5) clearInterval(lossFlashInterval); // On the fifth flash we stop
    }, 200);
}

/*
On click of a colour
 */
colorButton = $(".seg");
colorButton.click(function() {
    switch ($(this).attr("id")) {
        case "grn": input = 0;
            break;
        case "red": input = 1;
            break;
        case "blu": input = 2;
            break;
        case "ylw": input = 3;
            break;
    }
    nextGameState(input);
});

/**
 * Computes the next game state by choosing to either continue or lose the game.
 * @param input - value [0-3] of the button pressed.
 */
function nextGameState(input) {
    if(!standbyPhase) {
        if (input !== sequence[count]) {
            endGame();
        } else if (count === sequence.length - 1) {
            $(".current_score").html(++currentScore);
            updateHighScore();
            gameLoop();
        } else {
            count++;
        }
    }
}

function updateHighScore(){
    if(currentScore > highScore){
        highScore = currentScore;
        $(".high_score").html(highScore);
    }
}