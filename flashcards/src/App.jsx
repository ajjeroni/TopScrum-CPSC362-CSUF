import { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import { motion } from "framer-motion";
import { Plus, ArrowRight, CheckCircle, BarChart3 } from "lucide-react";

/* -------------------------------------------
   Simple Tailwind-based Components
------------------------------------------- */
const Card = ({ children, className, ...props }) => (
  <div className={`bg-blue-50 rounded-2xl shadow p-6 ${className}`} {...props}>{children}</div>
);
const CardContent = ({ children }) => <div>{children}</div>;
const Button = ({ children, className, ...props }) => (
  <button className={`bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 ${className}`} {...props}>{children}</button>
);
const Input = ({ className, ...props }) => (
  <input className={`border border-gray-300 rounded-lg p-2 ${className}`} {...props} />
);

/* -------------------------------------------
   Simple Spaced Repetition (SM-2 style lite)
------------------------------------------- */
const updateCardDifficulty = (card, quality) => {
  const newInterval = quality < 3 ? 1 : card.interval * 2;
  return {
    ...card,
    interval: newInterval,
    due: Date.now() + newInterval * 24 * 60 * 60 * 1000,
  };
};

/* -------------------------------------------
   Main App Router
------------------------------------------- */
export default function FlashcardApp() {
  const [cards, setCards] = useState([
    { id: 1, front: "React", back: "A JS library", interval: 1, due: Date.now() },
    { id: 2, front: "Supabase", back: "Open-source Firebase alt", interval: 1, due: Date.now() },
  ]);

  return (
    <Router>
      <div className="min-h-screen bg-gray-100 p-6">
        <nav className="flex gap-4 mb-6 text-lg font-semibold">
          <Link to="/">Dashboard</Link>
          <Link to="/study">Study Mode</Link>
          <Link to="/manage">Manage Cards</Link>
        </nav>

        <Routes>
          <Route path="/" element={<Dashboard cards={cards} />} />
          <Route path="/study" element={<Study cards={cards} setCards={setCards} />} />
          <Route path="/manage" element={<Manage cards={cards} setCards={setCards} />} />
        </Routes>
      </div>
    </Router>
  );
}

/* -------------------------------------------
   Dashboard Page
------------------------------------------- */
function Dashboard({ cards }) {
  const dueCards = cards.filter((c) => c.due <= Date.now());

  return (
    <div className="flex flex-col items-center gap-60">
      <h1 className="text-4xl font-bold">Dashboard</h1>
      <Card className="w-96">
        <CardContent className="text-center">
          <h2 className="text-2xl font-semibold mb-3">Study Overview</h2>
          <p className="text-xl">Total Cards: {cards.length}</p>
          <p className="text-xl text-green-600">Due Now: {dueCards.length}</p>
          <BarChart3 className="mx-auto mt-4 w-10 h-10" />
        </CardContent>
      </Card>
    </div>
  );
}

/* -------------------------------------------
   Study Page – Uses Spaced Repetition
------------------------------------------- */
function Study({ cards, setCards }) {
  const dueCards = cards.filter((c) => c.due <= Date.now());
  const [index, setIndex] = useState(0);
  const [showBack, setShowBack] = useState(false);

  if (dueCards.length === 0)
    return <h2 className="text-2xl text-center mt-20">No cards due right now 🎉</h2>;

  const current = dueCards[index];
  const answer = (quality) => {
    const updated = cards.map((c) => c.id === current.id ? updateCardDifficulty(c, quality) : c);
    setCards(updated);
    setShowBack(false);
    setIndex((i) => (i + 1) % dueCards.length);
  };

  return (
    <div className="flex flex-col items-center gap-6">
      <h1 className="text-3xl font-bold">Study Mode</h1>
      <motion.div
        key={current.id + String(showBack)}
        initial={{ opacity: 0, rotateY: 90 }}
        animate={{ opacity: 1, rotateY: 0 }}
        transition={{ duration: 0.4 }}
      >
        <Card className="w-96 h-56 flex items-center justify-center text-center cursor-pointer text-xl font-semibold" onClick={() => setShowBack(!showBack)}>
          <CardContent>{showBack ? current.back : current.front}</CardContent>
        </Card>
      </motion.div>

      {showBack && (
        <div className="flex gap-4">
          <Button onClick={() => answer(1)}>Hard</Button>
          <Button onClick={() => answer(3)}>Good</Button>
          <Button onClick={() => answer(5)}>
            <CheckCircle className="w-4 h-4 mr-1" /> Easy
          </Button>
        </div>
      )}
    </div>
  );
}

/* -------------------------------------------
   Manage Cards Page
------------------------------------------- */
function Manage({ cards, setCards }) {
  const [front, setFront] = useState("");
  const [back, setBack] = useState("");

  const add = () => {
    if (!front || !back) return;
    setCards([...cards, { id: Date.now(), front, back, interval: 1, due: Date.now() }]);
    setFront("");
    setBack("");
  };

  return (
    <div className="flex flex-col items-center gap-6">
      <h1 className="text-3xl font-bold">Manage Cards</h1>
      <Card className="w-full max-w-xl">
        <h2 className="text-2xl font-semibold mb-4">Add a Flashcard</h2>
        <div className="grid gap-4">
          <Input placeholder="Front" value={front} onChange={(e) => setFront(e.target.value)} />
          <Input placeholder="Back" value={back} onChange={(e) => setBack(e.target.value)} />
          <Button onClick={add}><Plus className="w-4 h-4 mr-1" /> Add Card</Button>
        </div>
      </Card>

      <div className="w-full max-w-xl grid gap-4">
        {cards.map((c) => (
          <Card key={c.id} className="p-4">
            <p className="font-semibold">{c.front}</p>
            <p className="text-gray-600">{c.back}</p>
            <p className="text-sm mt-2">Interval: {c.interval} days</p>
          </Card>
        ))}
      </div>
    </div>
  );
}
