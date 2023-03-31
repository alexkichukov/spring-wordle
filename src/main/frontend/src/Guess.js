const Guess = ({ guess }) => {
  return [...(guess?.word || '-----')].map((char, i) => (
    <span
      key={i}
      className={`Wordle-Match ${guess ? `Wordle-Match_${guess.matches.charAt(i)}` : ''}`}
    >
      {guess ? char : '\u00A0'}
    </span>
  ));
};

export default Guess;
