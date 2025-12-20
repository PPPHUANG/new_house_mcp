# Product Guidelines

## 1. Tone and Style
- **Informative & Professional:** The server should provide clear, structured, and factual information. While the end consumer is a user interacting with an AI, the immediate consumer is the AI itself.
- **Context-Rich:** Responses should include not just raw data but enough context (e.g., "3 beds, 2 baths" vs "3 bedroom, 2 bathroom detached house") to help the AI construct natural responses.
- **Objective:** Avoid marketing fluff. Descriptions should be neutral and focused on the property's verifiable attributes.

## 2. API & Tool Design Principles
- **Predictability:** Tools should have clear inputs and deterministic outputs.
- **Robust Error Handling:** The server should clearly communicate *why* a search failed (e.g., "No properties found in price range" vs just an empty list).
- **Conciseness:** JSON payloads should be free of redundant nesting or unnecessary metadata to save context window space.

## 3. Data Integrity
- **Mock Realism:** Even though the data is in-memory/mock, it should be realistic (e.g., valid addresses, logical price-per-square-foot ratios) to ensure testing and demos are meaningful.
