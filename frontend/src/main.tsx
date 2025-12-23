import { createContext, StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import UserStore from "./store/UserStore.ts"
import NotesStore from "./store/NoteStore.ts";

interface ContextValue {
    user: UserStore
    notes: NotesStore
}

export const Context = createContext<ContextValue>({} as ContextValue)

const rootElement = document.getElementById('root')
if (!rootElement) throw new Error('Root element not found')

createRoot(rootElement).render(
    <StrictMode>
        <Context.Provider
            value={{
                user: new UserStore(),
                notes: new NotesStore()
        }}>
            <App/>
        </Context.Provider>
    </StrictMode>,
)